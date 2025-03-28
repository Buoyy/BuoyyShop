package com.github.buoyy.shop.gui;

import com.github.buoyy.api.gui.GUIManager;
import com.github.buoyy.shop.Shop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GUIListener implements Listener {
    private final GUIManager manager = Shop.getGuiManager();
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        manager.handleClick(e);
    }
    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        manager.handleOpen(e);
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        manager.handleClose(e);
    }
}
