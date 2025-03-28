package com.github.buoyy.shop;

import com.github.buoyy.api.economy.CurrencyType;

public class ShopItem {
    // item should have a button which handles menus. the menus open by instance of this class
    int buyPrice, sellPrice, count; // count is -1 for general shop items
    CurrencyType currencyType;
}
