package com.github.buoyy.shop.gui;

import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.api.gui.InventoryGUI;
import com.github.buoyy.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MainMenuGUI extends InventoryGUI
{
    public MainMenuGUI() {
        this.inv = Bukkit.createInventory(null, 9, "Test");
    }

    @Override
    public void decorate() {
        InvButton general = InvButton.Builder.newBuilder()
                .setIcon(Material.WHEAT)
                .setName("General Shop")
                .setLore("A shop where you can buy and sell", "general items for", "reasonable prices.")
                .setOnClick(e->
                    Shop.getShopManager()
                            .openGeneralShopPage((Player)e.getWhoClicked(), 0))
                .build();
        InvButton yourShop = InvButton.Builder.newBuilder()
                .setIcon(Material.GREEN_SHULKER_BOX)
                .setName("Your Shop")
                .build();
        InvButton custom = InvButton.Builder.newBuilder()
                .setIcon(Material.ELYTRA)
                .setName("Custom Shop")
                .setLore("A shop where you can buy and sell", "your own items for", "(un)reasonable prices.")
                .setOnClick(e->
                    Shop.getShopManager()
                            .openShopkeepersList((Player)e.getWhoClicked()))
                .build();
        this.addButton(3, general);
        this.addButton(4, yourShop);
        this.addButton(5, custom);
        super.decorate();
    }
}
