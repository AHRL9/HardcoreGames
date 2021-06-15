package de.ahrl.hardcoregames.kit;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.player.HardcoreGamesPlayer;
import de.ahrl.hardcoregames.utility.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class Kit {
    private final String name;
    private final String description;
    private final int cooldown;
    private final ItemStack displayItem;
    private ItemStack kitItem;

    protected Kit(String name, String description, int cooldown, Material displayItemMaterial) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.displayItem = new Item(displayItemMaterial).setDisplayName(ChatColor.AQUA + name).setDescription(description).clearLore().create();
    }

    protected Kit(String name, String description, int cooldown, Material displayItemMaterial, Material kitItemMaterial, String kitItemName, String kitItemDescription) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.displayItem = new Item(displayItemMaterial).setDisplayName(ChatColor.AQUA + name).setDescription(description).clearLore().create();
        this.kitItem = new Item(kitItemMaterial).setDisplayName(ChatColor.AQUA + kitItemName).setDescription(kitItemDescription).clearLore().create();
    }

    public void onEnable(HardcoreGamesPlayer player) {

    }

    public void onDisable(HardcoreGamesPlayer player) {

    }
}
