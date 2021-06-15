package de.ahrl.hardcoregames.utility;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Kit {
    public static ArrayList<Kit> kitList = new ArrayList<>();
    public static HashMap<Player, Kit> kitUserList = new HashMap<>();
    private HashMap<Player, Long> cooldownList = new HashMap<>();
    private ItemStack kitItem;
    private ItemStack kitSelectorItem;
    private String name;
    private String id;
    private String description;
    private int cooldown;

    public Kit(String name, String description, int cooldown, Material kitSelectorMaterial) {
        this.name = name;
        this.id = name.replaceAll("\\s","").toLowerCase();
        this.description = description;
        this.cooldown = cooldown;
        kitList.add(this);
        kitList.sort((kit1, kit2) -> kit1.getName().compareToIgnoreCase(kit2.getName()));
        kitSelectorItem = new Item(kitSelectorMaterial).setDisplayName(ChatColor.AQUA + name).setDescription(description).clearLore().create();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasKitItem() {
        return getKitItem() != null;
    }

    public void addToPlayer(Player player) {
        kitUserList.put(player, this);
        if(HardcoreGamesMain.getGameStatus().equals(GameStatus.TESTMODE) && hasKitItem()) {
            addKitItemToPlayer(player);
        }
    }

    public ArrayList<Player> getUserList() {
        ArrayList<Player> userList = new ArrayList<>();
        for (Player player : kitUserList.keySet()) {
            if(hasUser(player)) {
                userList.add(player);
            }
        }
        return userList;
    }

    public Kit setKitItem(Material material, String name, String description) {
        kitItem = new Item(material).setDisplayName(ChatColor.AQUA + name).setDescription(ChatColor.DARK_PURPLE + description).isGlowing().clearLore().isUndroppable().create();
        return this;
    }

    public void addKitItemToPlayer(Player player) {
        player.getInventory().addItem(kitItem);
    }

    public void addCooldown(Player player) {
        cooldownList.put(player, cooldown * 1000L + System.currentTimeMillis());
    }

    public boolean hasUser(Player player) {
        return kitUserList.containsKey(player) && kitUserList.get(player).equals(this);
    }

    public boolean hasCooldown(Player player) {
        return cooldownList.containsKey(player) && cooldownList.get(player) > System.currentTimeMillis();
    }

    public int getCooldownFromPlayer(Player player) {
        return (int) ((cooldownList.get(player) - System.currentTimeMillis()) / 1000) + 1;
    }

    public int getCooldown() {
        return cooldown;
    }

    public String getName() {
        return name;
    }

    public ItemStack getKitItem() {
        return kitItem;
    }

    public ItemStack getKitSelectorItem() {
        return kitSelectorItem;
    }
}
