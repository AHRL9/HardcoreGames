package de.ahrl.hardcoregames.utility;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Item {
    public static ArrayList<ItemStack> undroppableItemList = new ArrayList<>();
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public Item(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public Item isGlowing() {
        itemMeta.addEnchant(Enchantment.MENDING, 0, false);
        return this;
    }

    public Item isUndroppable() {
        undroppableItemList.add(itemStack);
        return this;
    }

    public Item setDisplayName(String displayName) {
        itemMeta.setDisplayName(displayName);
        return this;
    }

    public Item setDescription(String lore) {
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(lore);
        itemMeta.setLore(itemLore);
        return this;
    }

    public Item setLore(ArrayList<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public Item clearLore() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemStack create() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
