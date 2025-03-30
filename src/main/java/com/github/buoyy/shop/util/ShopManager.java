package com.github.buoyy.shop.util;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.economy.IEconomy;
import com.github.buoyy.api.economy.Transaction;
import com.github.buoyy.api.inputchat.InputType;
import com.github.buoyy.api.inputchat.event.ChatInputStartEvent;
import com.github.buoyy.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShopManager {
    private final IEconomy economy = Shop.getEconomy();
    private final Map<Player, ShopItem> playerItemMap = new HashMap<>();
    public void buyItem(Player player, ShopItem item, int count)
    {
        int totalPrice = item.buy * count;
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
    public void sellItem(Player player, ShopItem item, int count)
    {
        int totalPrice = item.sell * count;
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
    public void addPlayerWithItem(Player player, ShopItem item)
    {
        this.playerItemMap.put(player, item);
    }
    public ShopItem getItemByPlayer(Player player)
    {
        return this.playerItemMap.get(player);
    }
    private void dropOnPlayer(Player player, ItemStack itemStack)
    {
        player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
    }
    public void startChatInput(Player sender, CurrencyType currencyType,
                               InputType inputType, ShopItem item)
    {
        ChatInputStartEvent event = new ChatInputStartEvent(sender, null,
                currencyType, inputType);
        Bukkit.getPluginManager().callEvent(event);
        this.addPlayerWithItem(sender, item);
    }
}
