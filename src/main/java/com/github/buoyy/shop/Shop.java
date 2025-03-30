package com.github.buoyy.shop;

import com.github.buoyy.api.command.BaseCommand;
import com.github.buoyy.api.economy.IEconomy;
import com.github.buoyy.api.file.YAML;
import com.github.buoyy.api.gui.GUIManager;
import com.github.buoyy.api.util.Messenger;
import com.github.buoyy.shop.gui.GUIListener;
import com.github.buoyy.shop.gui.MainMenuGUI;
import com.github.buoyy.shop.util.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Shop extends JavaPlugin {
    private static Messenger messenger;
    private static IEconomy economy = null;
    private static GUIManager guiManager;
    private static YAML generalShop;
    private static ShopManager shopManager;
    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!hookEcon())
        {
            messenger.consoleBad("Couldn't hook with economy! Is BuoyyEcon present/functional?");
            return;
        }
        saveDefaultConfig();
        instanceObjects();
        messenger.consoleGood("Hooked with BuoyyEcon.");
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new ChatInputListener(), this);
        BaseCommand shopCmd = new BaseCommand("/shop", player -> guiManager.openGUI(player, new MainMenuGUI()));
        Objects.requireNonNull(getCommand("shop")).setExecutor(shopCmd);
    }

    private void instanceObjects()
    {
        messenger = new Messenger("SHOP");
        guiManager = new GUIManager();
        generalShop = new YAML(this.getName(), "general", messenger);
        shopManager = new ShopManager();
    }

    private boolean hookEcon() {
        RegisteredServiceProvider<IEconomy> rsp = Bukkit.getServicesManager().getRegistration(IEconomy.class);
        if (rsp == null)
            return false;
        economy = rsp.getProvider();
        return true;
    }
    public static GUIManager getGuiManager() {
        return guiManager;
    }
    public static YAML getGeneralShop() {
        return generalShop;
    }
    public static IEconomy getEconomy() {
        return economy;
    }
    public static ShopManager getShopManager() {
        return shopManager;
    }
}
