package com.github.buoyy.shop.gui.general;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.api.gui.InventoryGUI;
import com.github.buoyy.shop.Shop;
import com.github.buoyy.shop.gui.MainMenuGUI;
import com.github.buoyy.shop.util.GeneralShopItem;
import com.github.buoyy.shop.util.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GeneralShopPage extends InventoryGUI
{
    private final int index;
    private final FileConfiguration shop;
    private final ShopManager manager;
    public GeneralShopPage(int index)
    {
        this.index = index;
        this.inv = Bukkit.createInventory(null, 54, "General Shop: Page "+(index+1));
        this.prevInv = new MainMenuGUI();
        manager = Shop.getShopManager();
        shop = Shop.getGeneralShop().getConfig();
    }

    @Override
    public void decorate()
    {
        addAllItems();
        if (index > 0) // Not the first page, so we can have a prevPage button
        {
            InvButton prevPage = InvButton.Builder.newBuilder()
                    .setIcon(Material.ARROW)
                    .setName("Previous Page")
                    .setOnClick(e->
                            Shop.getShopManager().openGeneralShopPage((Player)e.getWhoClicked(), index-1))
                    .build();
            this.addButton(47, prevPage);
        }
        if (index < manager.g_totalPages-1)// Not the last page, so we can have a nextPage button
        {
            InvButton nextPage = InvButton.Builder.newBuilder()
                    .setIcon(Material.ARROW)
                    .setName("Next Page")
                    .setOnClick(e->
                            Shop.getShopManager().openGeneralShopPage((Player)e.getWhoClicked(), index+1))
                    .build();
            this.addButton(51, nextPage);
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
        boolean prioritiseStack = shop.getBoolean(i+".is-stack");
        int buyPrice = Integer.parseInt(Objects.requireNonNull(shop.getString(i + ".buy")));
        int sellPrice = Integer.parseInt(Objects.requireNonNull(shop.getString(i + ".sell")));
        GeneralShopItem item = new GeneralShopItem(material, currency, buyPrice,
                sellPrice, index, prioritiseStack);
        this.addButton(i-(index*45), item.button);
    }
    private void addAllItems()
    {
        int start = index*45;
        int end = (index < manager.g_totalPages-1) ?
                (index+1)*45 : manager.g_totalItems;
        for (int i = start; i < end; i++)
            addItemFromFile(i);
    }
}
