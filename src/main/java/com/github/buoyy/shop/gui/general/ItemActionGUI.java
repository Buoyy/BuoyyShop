package com.github.buoyy.shop.gui.general;

import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.api.gui.InventoryGUI;
import com.github.buoyy.api.inputchat.InputType;
import com.github.buoyy.shop.Shop;
import com.github.buoyy.shop.util.GeneralShopItem;
import com.github.buoyy.shop.util.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ItemActionGUI extends InventoryGUI {
    private final GeneralShopItem item;
    private final Player sender;
    private final ShopManager manager;
    private final boolean isBuyMenu;
    public ItemActionGUI(GeneralShopItem item, Player sender, boolean isBuyMenu)
    {
        this.inv = Bukkit.createInventory(null, 18,
                isBuyMenu ? "Buy" : "Sell");
        this.prevInv = new GeneralShopPage(0);
        this.item = item;
        this.sender = sender;
        this.isBuyMenu = isBuyMenu;
        this.manager = Shop.getShopManager();
    }

    @Override
    public void decorate() {
        if (isBuyMenu)
            decorateBuyMenu();
        else
            decorateSellMenu();
        super.decorate();
    }
    private void decorateBuyMenu()
    {
        InvButton buy1 = InvButton.Builder.newBuilder()
                .setIcon(Material.GREEN_CARPET)
                .setName(item.prioritiseStack ?
                        "Buy 1 stack" : "Buy 1")
                .setOnClick(e->
                {
                    manager.buyGeneralItem((Player) e.getWhoClicked(), item,
                            item.prioritiseStack ? 64 : 1);
                    e.getWhoClicked().closeInventory();
                })
                .build();
        InvButton buy64 = InvButton.Builder.newBuilder()
                .setIcon(Material.GREEN_WOOL)
                .setName(item.prioritiseStack ? "Buy 4 stacks" : "Buy 64")
                .setOnClick(e->
                {
                    manager.buyGeneralItem((Player) e.getWhoClicked(), item,
                            item.prioritiseStack ? 256 : 64);
                    e.getWhoClicked().closeInventory();
                })
                .build();
        InvButton buyChoice = InvButton.Builder.newBuilder()
                .setIcon(Material.GREEN_BANNER)
                .setName(item.prioritiseStack ?
                        "Buy (?) stacks" : "Buy (?)")
                .setOnClick(e->
                {
                    Shop.getShopManager().startChatInput(sender, item.currency,
                            item.prioritiseStack ? InputType.BUY_STACK : InputType.BUY,
                            this.item);
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
    }
    private void decorateSellMenu()
    {
        InvButton sell1 = InvButton.Builder.newBuilder()
                .setIcon(Material.CYAN_CARPET)
                .setName(item.prioritiseStack ? "Sell 1 stack" : "Sell 1")
                .setOnClick(e->
                {
                    manager.sellGeneralItem((Player) e.getWhoClicked(), item,
                            item.prioritiseStack ? 64 : 1);
                    e.getWhoClicked().closeInventory();
                })
                .build();
        InvButton buy64 = InvButton.Builder.newBuilder()
                .setIcon(Material.CYAN_WOOL)
                .setName(item.prioritiseStack ? "Sell 4 stacks" : "Sell 64")
                .setOnClick(e->
                {
                    manager.sellGeneralItem((Player) e.getWhoClicked(), item,
                            item.prioritiseStack ? 256 : 64);
                    e.getWhoClicked().closeInventory();
                })
                .build();
        InvButton buyChoice = InvButton.Builder.newBuilder()
                .setIcon(Material.CYAN_BANNER)
                .setName("Sell (?)")
                .setOnClick(e->
                {
                    Shop.getShopManager().startChatInput(sender, item.currency,
                            item.prioritiseStack ? InputType.SELL_STACK : InputType.SELL,
                            this.item);
                    e.getWhoClicked().closeInventory();
                })
                .build();
        InvButton back = InvButton.Builder.newBuilder()
                .setIcon(Material.RED_WOOL)
                .setName("Back")
                .setOnClick(e-> Shop.getGuiManager()
                        .openGUI((Player)e.getWhoClicked(), this.prevInv))
                .build();
        this.addButton(3, sell1);
        this.addButton(4, buyChoice);
        this.addButton(5, buy64);
        this.addButton(17, back);
    }
}
