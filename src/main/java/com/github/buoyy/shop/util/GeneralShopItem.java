package com.github.buoyy.shop.util;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.shop.Shop;
import com.github.buoyy.shop.gui.general.ItemBuySellGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GeneralShopItem {
    // item should have a button which handles menus. the menus open by instance of this class
    public int buy, sell;// count is -1 for general shop items
    public Material material;
    public InvButton button;
    public CurrencyType currency;
    public boolean prioritiseStack;
    public GeneralShopItem(Material material, CurrencyType currency, int buy,
                           int sell, int pageIndex, boolean prioritiseStack)
    {
        this.material = material;
        this.currency = currency;
        this.buy = buy;
        this.sell = sell;
        this.prioritiseStack = prioritiseStack;
        this.button = InvButton.Builder.newBuilder()
                .setIcon(material)
                .setLore((prioritiseStack) ?
                                "Buy 64 for "+Shop.getEconomy().format(buy, currency) :
                                "Buy 1 for "+Shop.getEconomy().format(buy, currency),
                        (prioritiseStack) ?
                                "Sell 64 for "+Shop.getEconomy().format(sell, currency) :
                                "Sell 1 for "+Shop.getEconomy().format(sell, currency))
                .setOnClick(e->
                        openItemMenu((Player) e.getWhoClicked(), pageIndex))
                .build();
    }
    private void openItemMenu(Player player, int pageIndex)
    {
        Shop.getGuiManager().openGUI(player, new ItemBuySellGUI(this, pageIndex));
    }
}
