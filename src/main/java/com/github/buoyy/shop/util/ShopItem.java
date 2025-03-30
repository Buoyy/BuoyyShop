package com.github.buoyy.shop.util;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.shop.Shop;
import com.github.buoyy.shop.gui.ItemBuySellGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ShopItem {
    // item should have a button which handles menus. the menus open by instance of this class
    public int buy, sell, count;// count is -1 for general shop items
    public Material material;
    public InvButton button;
    public CurrencyType currency;
    public ShopItem(Material material, CurrencyType currency, int buy, int sell, int count)
    {
        this.material = material;
        this.currency = currency;
        this.buy = buy;
        this.sell = sell;
        this.count = count;
        this.button = InvButton.Builder.newBuilder()
                .setIcon(material)
                .setLore("Buy 1 for "+Shop.getEconomy().format(buy, currency),
                        "Sell 1 for "+Shop.getEconomy().format(sell, currency))
                .setOnClick(e->
                        openItemMenu((Player) e.getWhoClicked()))
                .build();
    }
    private void openItemMenu(Player player)
    {
        Shop.getGuiManager().openGUI(player, new ItemBuySellGUI(this));
    }
}
