package com.github.buoyy.shop.util;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.Objects;

// Even though it's called "pack", it could also possibly represent
// a single slot worth of item(s).
public class ItemPack {
    public ItemStack icon;
    public CurrencyType curr;
    public int cost;
    private InvButton button;

    public ItemPack(ItemStack[] items, CurrencyType curr, int cost) {
        this.curr = curr;
        this.cost = cost;
        this.icon = createPackIcon(items);
        this.initButton();
    }

    public InvButton getButton()
    {
        return button;
    }

    private ItemStack createPackIcon(ItemStack[] items) {
        ItemStack icon;
        if (items.length != 1)
        {
            icon = new ItemStack(Material.SHULKER_BOX);
            BlockStateMeta meta = (BlockStateMeta) icon.getItemMeta();
            if (meta == null) return icon;
            ShulkerBox box = (ShulkerBox) meta.getBlockState();
            for (ItemStack item : items) {
                if (item != null)
                    box.getInventory().addItem(item);
            }
            box.update();
            meta.setBlockState(box);
            icon.setItemMeta(meta);
        } else
            icon = items[0];
        return icon;
    }

    private void initButton() {
        this.button = InvButton.Builder.newBuilder()
                .setIcon(icon)
                .setLore(String.valueOf(Objects.requireNonNull(icon.getItemMeta()).getLore()),
                        "Cost: "+cost)
                //.setOnClick(manager.buyFromPlayer(shopkeeper, pack))
                .build();
    }
}
