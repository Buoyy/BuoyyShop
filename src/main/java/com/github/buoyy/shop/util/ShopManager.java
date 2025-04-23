package com.github.buoyy.shop.util;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.economy.IEconomy;
import com.github.buoyy.api.economy.Transaction;
import com.github.buoyy.api.file.YAML;
import com.github.buoyy.api.inputchat.InputType;
import com.github.buoyy.api.inputchat.event.ChatInputStartEvent;
import com.github.buoyy.shop.Shop;
import com.github.buoyy.shop.gui.general.GeneralShopPage;
import com.github.buoyy.shop.gui.custom.ShopkeepersGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ShopManager {
    private final IEconomy economy = Shop.getEconomy();
    private final FileConfiguration config = Shop.getInstance().getConfig();
    private final Map<Player, GeneralShopItem> playerItemMap = new HashMap<>();
    private final HashMap<UUID, YAML> shopkeeperShopMap = new HashMap<>();

    public int g_totalPages = (int)Math.ceil(Shop.getGeneralShop().getConfig()
            .getKeys(false).size()/45f);
    public int g_totalItems = Shop.getGeneralShop().getConfig().getKeys(false).size();

    public void buyGeneralItem(Player player, GeneralShopItem item, int count)
    {
        int totalPrice = item.buy * (item.prioritiseStack ? count/64 : count);
        Transaction subtract = economy.subtract(player, item.currency, totalPrice);
        if (!subtract.isSuccessful())
        {
            player.sendMessage("Error during buying: " + subtract.message);
            return;
        }
        player.sendMessage("You bought "+count+" for "+economy.format(totalPrice, item.currency));
        ItemStack toDrop = new ItemStack(item.material, count);
        dropOnPlayer(player, toDrop);
    }
    public void sellGeneralItem(Player player, GeneralShopItem item, int count)
    {
        int totalPrice = item.sell * (item.prioritiseStack ? count/64 : count);
        if (!player.getInventory().contains(item.material, count))
        {
            player.sendMessage(count+" items weren't found in your inv.");
            return;
        }
        Transaction add = economy.add(player, item.currency, totalPrice);
        if (!add.isSuccessful())
        {
            player.sendMessage("Error during selling: " + add.message);
            return;
        }
        player.sendMessage("You sold "+count+" for "+economy.format(totalPrice, item.currency));
        player.getInventory().removeItem(new ItemStack(item.material, count));
    }
    private void addPlayerWithItem(Player player, GeneralShopItem item)
    {
        this.playerItemMap.put(player, item);
    }
    public GeneralShopItem getItemByPlayer(Player player)
    {
        return this.playerItemMap.get(player);
    }
    private void dropOnPlayer(Player player, ItemStack itemStack)
    {
        player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
    }
    public void startChatInput(Player sender, CurrencyType currencyType,
                               InputType inputType, GeneralShopItem item)
    {
        ChatInputStartEvent event = new ChatInputStartEvent(sender, null,
                currencyType, inputType);
        Bukkit.getPluginManager().callEvent(event);
        this.addPlayerWithItem(sender, item);
    }
    public void openGeneralShopPage(Player player, int index)
    {
        Shop.getGuiManager().openGUI(player, new GeneralShopPage(index));
    }
    public void openShopkeepersList(Player player)
    {
        Shop.getGuiManager().openGUI(player, new ShopkeepersGUI(player));
    }
    public void loadKeeper(OfflinePlayer player)
    {
        // Add UUID in config
        List<String> keepers = config.getStringList("shopkeepers");
        if (!keepers.contains(player.getUniqueId().toString()))
        {
            keepers.add(player.getUniqueId().toString());
            config.set("shopkeepers", keepers);
            Shop.getInstance().saveConfig();
        }
        // Create shop YAML, if not already created
        YAML file = new YAML(Shop.getInstance().getName(),
                "shops/"+player.getUniqueId(), Shop.getMessenger());
        shopkeeperShopMap.put(player.getUniqueId(), file);
    }
    public void loadExistingKeepers()
    {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers())
        {
            loadKeeper(player);
        }
    }
    public void registerPack(OfflinePlayer keeper, ItemPack pack)
    {
        YAML file = shopkeeperShopMap.get(keeper.getUniqueId());
        int currentAmount = file.getConfig().getKeys(false).size();
        file.getConfig().set(currentAmount+".curr", pack.curr);
        file.getConfig().set(currentAmount+".cost", pack.cost);
        file.getConfig().set(currentAmount+".pack", pack.icon);
    }
}
