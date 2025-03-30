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
    FileConfiguration shop;
    public GeneralShopGUI()
    {
        this.inv = Bukkit.createInventory(null, 54, "General Shop");
        this.prevInv = new MainMenuGUI();
        shop = Shop.getGeneralShop().getConfig();
    }

    @Override
    public void decorate()
    {
        for (String i : shop.getKeys(false))
        {
            addItemFromFile(Integer.parseInt(i));
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
