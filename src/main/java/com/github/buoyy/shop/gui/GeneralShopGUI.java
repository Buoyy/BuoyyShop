package com.github.buoyy.shop.gui;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.api.gui.InventoryGUI;
import com.github.buoyy.shop.Shop;
import com.github.buoyy.shop.util.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GeneralShopGUI extends InventoryGUI
{
    private final int index;
    private final FileConfiguration shop;
    public GeneralShopGUI(int index)
    {
        this.inv = Bukkit.createInventory(null, 54, "General Shop");
        this.prevInv = new MainMenuGUI();
        this.index = index;
        shop = Shop.getGeneralShop().getConfig();
    }

    @Override
    public void decorate()
    {
        for (int i = index*45; i < 45*(index+1); i++)
        {
            addItemFromFile(i);
        }
        if (index > 0)
        {
            InvButton prevPage = InvButton.Builder.newBuilder()
                    .setIcon(Material.ARROW)
                    .setName("Previous Page")
                    .setOnClick(e->
                            Shop.getShopManager().openShopPage((Player)e.getWhoClicked(), index-1))
                    .build();
            this.addButton(46, prevPage);
        }
        if (index < Shop.getShopManager().getTotalPages()-1)
        {
            InvButton nextPage = InvButton.Builder.newBuilder()
                    .setIcon(Material.ARROW)
                    .setName("Next Page")
                    .setOnClick(e->
                            Shop.getShopManager().openShopPage((Player)e.getWhoClicked(), index+1))
                    .build();
            this.addButton(52, nextPage);
        }
        InvButton back = InvButton.Builder.newBuilder()
                .setIcon(Material.RED_WOOL)
                .setName("Back")
                .setOnClick(e->Shop.getGuiManager()
                        .openGUI((Player)e.getWhoClicked(), this.prevInv))
                .build();
        this.addButton(53, back);
        super.decorate();
    }

    private void addItemFromFile(int i)
    {
        Material material = Material.valueOf(shop.getString(i+".item"));
        CurrencyType currency = CurrencyType.valueOf(shop.getString(i+".currency"));
        int buyPrice = Integer.parseInt(Objects.requireNonNull(shop.getString(i + ".buy")));
        int sellPrice = Integer.parseInt(Objects.requireNonNull(shop.getString(i + ".sell")));
        ShopItem item = new ShopItem(material, currency, buyPrice, sellPrice, -1);
        this.addButton(i, item.button);
    }
}
