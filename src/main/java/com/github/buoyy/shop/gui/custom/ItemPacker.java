package com.github.buoyy.shop.gui.custom;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InventoryHandler;
import com.github.buoyy.shop.util.ItemPack;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class ItemPacker extends InventoryHandler {
    private final CurrencyType curr;
    public ItemPacker(CurrencyType curr)
    {
        this.inv = Bukkit.createInventory(null, InventoryType.SHULKER_BOX, "Register: ");
        this.curr = curr;
    }

    @Override
    public void onClose(InventoryCloseEvent e)
    {

    }

    @Override
    public void onClick(InventoryClickEvent inventoryClickEvent) {}

    @Override
    public void onOpen(InventoryOpenEvent inventoryOpenEvent) {}
}
