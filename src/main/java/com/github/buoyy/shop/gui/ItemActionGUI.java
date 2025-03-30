package com.github.buoyy.shop.gui;

import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.api.gui.InventoryGUI;
import com.github.buoyy.api.inputchat.InputType;
import com.github.buoyy.shop.Shop;
import com.github.buoyy.shop.util.ShopItem;
import com.github.buoyy.shop.util.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ItemActionGUI extends InventoryGUI {
    private final ShopItem item;
    private final Player sender;
    private final ShopManager manager;
    public ItemActionGUI(ShopItem item, Player sender)
    {
        this.inv = Bukkit.createInventory(null, 18, "Buy/Sell item?");
        this.prevInv = new GeneralShopGUI();
        this.item = item;
        this.sender = sender;
        this.manager = Shop.getShopManager();
    }

    @Override
    public void decorate() {
        InvButton buy1 = InvButton.Builder.newBuilder()
                .setIcon(Material.GREEN_CARPET)
                .setName("Buy 1")
                .setOnClick(e->
                {
                    manager.buyItem((Player) e.getWhoClicked(), item, 1);
                    e.getWhoClicked().closeInventory();
                })
                .build();
        InvButton buy64 = InvButton.Builder.newBuilder()
                .setIcon(Material.GREEN_WOOL)
                .setName("Buy 64")
                .setOnClick(e->
                {
                    manager.buyItem((Player) e.getWhoClicked(), item, 64);
                    e.getWhoClicked().closeInventory();
                })
                .build();
        InvButton buyChoice = InvButton.Builder.newBuilder()
                .setIcon(Material.GREEN_SHULKER_BOX)
                .setName("Buy (?)")
                .setOnClick(e->
                {
                    Shop.getShopManager().startChatInput(sender, item.currency,
                            InputType.SHOP_BUY, this.item);
                    e.getWhoClicked().closeInventory();
                })
                .build();
        InvButton back = InvButton.Builder.newBuilder()
                .setIcon(Material.RED_WOOL)
                .setName("Back")
                .setOnClick(e-> Shop.getGuiManager()
                        .openGUI((Player)e.getWhoClicked(), this.prevInv))
                .build();
        this.addButton(3, buy1);
        this.addButton(4, buyChoice);
        this.addButton(5, buy64);
        this.addButton(17, back);
        super.decorate();
    }
}
