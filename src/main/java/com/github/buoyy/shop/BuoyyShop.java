package com.github.buoyy.shop;

import com.github.buoyy.api.economy.IEconomy;
import com.github.buoyy.api.util.Messenger;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class BuoyyShop extends JavaPlugin
{
    private static IEconomy econ;
    private static final Messenger messenger = new Messenger();
    @Override
    public void onEnable()
    {
        // Plugin startup logic
        if (!hookEconomy())
        {
            messenger.consoleBad("Economy plugin was not found!");
            return;
        }
        messenger.consoleGood("Plugin loaded with economy.");
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }

    private boolean hookEconomy()
    {
        RegisteredServiceProvider<IEconomy> rsp = getServer().getServicesManager().getRegistration(IEconomy.class);
        if (rsp != null)
        {
            econ = rsp.getProvider();
        }
        return econ != null;
    }

    public static IEconomy getEcon() {
        return econ;
    }
}
