package com.github.buoyy.shop.gui;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.file.YAML;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.api.gui.InventoryGUI;
import com.github.buoyy.api.gui.InventoryHandler;
import com.github.buoyy.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GeneralShopGUI extends InventoryGUI
{
    public GeneralShopGUI(InventoryHandler prevInv)
    {
        this.inv = Bukkit.createInventory(null, 54, "General Shop");
        this.prevInv = prevInv;
    }

    @Override
    public void decorate()
    {
        FileConfiguration shop = Shop.getGeneralShop().getConfig();
        for (String i : shop.getKeys(false))
        {
            Material icon = Material.valueOf(shop.getString(i+".item"));
            // CurrencyType currency = CurrencyType.valueOf(shop.getString(i+".currency"));
            int buyPrice = Integer.parseInt(shop.getString(i+".buy"));
            int sellPrice = Integer.parseInt(shop.getString(i+".sell"));
            InvButton item = InvButton.Builder.newBuilder()
                    .setIcon(icon)
                    .setLore("Cost: "+buyPrice, "Sell: "+sellPrice)
                    .build();
            this.addButton(Integer.parseInt(i), item);
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
}
