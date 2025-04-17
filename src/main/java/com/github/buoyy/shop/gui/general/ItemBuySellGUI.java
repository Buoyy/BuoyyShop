package com.github.buoyy.shop.gui.general;

import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.api.gui.InventoryGUI;
import com.github.buoyy.shop.Shop;
import com.github.buoyy.shop.util.GeneralShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ItemBuySellGUI extends InventoryGUI {
    private final GeneralShopItem item;
    public ItemBuySellGUI(GeneralShopItem item, int pageIndex)
    {
        this.inv = Bukkit.createInventory(null, 9, "Buy or Sell?");
        this.prevInv = new GeneralShopPage(pageIndex);
        this.item = item;
    }

    @Override
    public void decorate() {
        InvButton buy = InvButton.Builder.newBuilder()
                .setIcon(Material.GREEN_STAINED_GLASS)
                .setName("Buy item")
                .setOnClick(e-> Shop.getGuiManager()
                        .openGUI((Player)e.getWhoClicked(),
                                new ItemActionGUI(item, (Player) e.getWhoClicked(), true)))
                .build();
        InvButton sell = InvButton.Builder.newBuilder()
                .setIcon(Material.CYAN_STAINED_GLASS)
                .setName("Sell item")
                .setOnClick(e-> Shop.getGuiManager()
                        .openGUI((Player)e.getWhoClicked(),
                                new ItemActionGUI(item, (Player) e.getWhoClicked(), false)))
                .build();
        InvButton back = InvButton.Builder.newBuilder()
                .setIcon(Material.RED_WOOL)
                .setName("Back")
                .setOnClick(e-> Shop.getGuiManager()
                        .openGUI((Player)e.getWhoClicked(), this.prevInv))
                .build();
        this.addButton(3, buy);
        this.addButton(5, sell);
        this.addButton(8, back);
        super.decorate();
    }
}
