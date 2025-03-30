package com.github.buoyy.shop;

import com.github.buoyy.api.inputchat.event.ChatInputProcEvent;
import com.github.buoyy.shop.util.ShopManager;
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
        switch (e.getInputType())
        {
            case SHOP_BUY ->
                manager.buyItem(sender, manager.getItemByPlayer(sender.getPlayer()),
                        Integer.parseInt(e.getInput()));
            case SHOP_SELL -> manager.sellItem(sender, manager.getItemByPlayer(sender.getPlayer()),
                    Integer.parseInt(e.getInput()));
        }
    }
}
