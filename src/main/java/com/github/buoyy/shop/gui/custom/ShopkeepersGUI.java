package com.github.buoyy.shop.gui.custom;

import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.api.gui.InventoryGUI;
import com.github.buoyy.shop.gui.MainMenuGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ShopkeepersGUI extends InventoryGUI {
    private final OfflinePlayer player;
    public ShopkeepersGUI(Player player)
    {
        this.inv = Bukkit.createInventory(null, 54, "Shopkeepers");
        this.prevInv = new MainMenuGUI();
        this.player = player;
    }

    @Override
    public void decorate() {
        int i = 0;
        for (OfflinePlayer p : Bukkit.getOfflinePlayers())
        {
            if (p == player) continue;
            InvButton shopkeeper = InvButton.Builder.newBuilder()
                    .setIcon(getPlayerHead(p))
                    .setName(p.getName())
                    .build();
            this.addButton(i, shopkeeper);
            i++;
        }
        super.decorate();
    }

    ItemStack getPlayerHead(OfflinePlayer player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null)
            meta.setOwningPlayer(player);
        head.setItemMeta(meta);
        return head;
    }
}
