package de.ahrl.hardcoregames.utility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Tracker implements Listener {
    private static ItemStack tracker = new Item(Material.COMPASS)
            .isGlowing()
            .isUndroppable()
            .setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Tracker")
            .setDescription("Right-click to track the nearest player!")
            .clearLore()
            .create();
    private HashMap<Player, Player> trackerPairs = new HashMap<>();

    public static void giveCompassToPlayer(Player player) {
        player.getInventory().addItem(tracker);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Player trackedPlayer = null;
        if (event.getItem() == null) {
            return;
        }
        if(event.getItem().equals(tracker)) {
            double lastDistance = Double.MAX_VALUE;
            for (Player playerInWorld : player.getWorld().getPlayers()) {
                if(playerInWorld != player) {
                    double distance = player.getLocation().distance(playerInWorld.getLocation());
                    if(distance < lastDistance) {
                        lastDistance = distance;
                        trackedPlayer = playerInWorld;
                    }
                }
            }
            if(trackedPlayer != null) {
                if(trackedPlayer.getWorld().equals(player.getWorld())) {
                    player.sendMessage(ChatColor.GRAY + "You are now tracking " + ChatColor.AQUA + trackedPlayer.getDisplayName() + ChatColor.GRAY + "!");
                    trackerPairs.put(trackedPlayer, player);
                    player.setCompassTarget(trackedPlayer.getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(trackerPairs.containsKey(player)) {
            if(trackerPairs.get(player) != null) {
                if(player.getWorld().equals(trackerPairs.get(player).getWorld())) {
                    trackerPairs.get(player).setCompassTarget(player.getLocation());
                }
            }
        }
    }
}
