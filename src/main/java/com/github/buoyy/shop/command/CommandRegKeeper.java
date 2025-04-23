package com.github.buoyy.shop.command;

import com.github.buoyy.api.command.SubCommand;
import com.github.buoyy.shop.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandRegKeeper implements SubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player player)
        {
            Shop.getShopManager().loadKeeper(player);
            Shop.getMessenger().consoleOK("Registered "+player.getName()+" as a shopkeeper");
        }
        return true;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        return List.of();
    }
}
