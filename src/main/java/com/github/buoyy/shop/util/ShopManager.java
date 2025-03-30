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
        int cost = item.buy;
        Transaction subtract = economy.subtract(player, item.currency, cost*count);
        if (!subtract.isSuccessful())
            player.sendMessage("Error during buying: "+subtract.message);
        player.sendMessage("You bought "+count+" for "+Shop.getEconomy().format(cost*count, item.currency));
        ItemStack toDrop = new ItemStack(item.material, count);
        dropOnPlayer(player, toDrop);
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
