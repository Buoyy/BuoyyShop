package com.github.buoyy.shop;

import com.github.buoyy.api.inputchat.event.ChatInputProcEvent;
import com.github.buoyy.shop.util.ShopManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatInputListener implements Listener
{
    ShopManager manager = Shop.getShopManager();
    @EventHandler
    public void onPlayerChatInput(ChatInputProcEvent e)
    {
        Player sender = e.getSender();
        final int input;
        try {
            input = Integer.parseInt(e.getInput());
        }
        catch (NumberFormatException ex) {
            sender.sendMessage(ChatColor.RED+"That's not a number!");
            return;
        }
        switch (e.getInputType())
        {
            case BUY ->
                manager.buyGeneralItem(sender, manager.getItemByPlayer(sender.getPlayer()),
                        input);
            case SELL ->
                    manager.sellGeneralItem(sender, manager.getItemByPlayer(sender.getPlayer()),
                    input);
            case BUY_STACK ->
                    manager.buyGeneralItem(sender, manager.getItemByPlayer(sender.getPlayer()),
                            manager.getItemByPlayer(sender.getPlayer()).getStackSize()*input);
            case SELL_STACK ->
                    manager.sellGeneralItem(sender, manager.getItemByPlayer(sender.getPlayer()),
                            manager.getItemByPlayer(sender.getPlayer()).getStackSize()*input);
        }
    }
}
