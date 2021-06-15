package de.ahrl.hardcoregames.resource;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.utility.Item;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SpectateSystem implements Listener {
    public static ArrayList<Player> livingPlayerList = new ArrayList<>();

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event) {
        event.setCancelled(true);
        livingPlayerList.remove(event.getEntity());
        checkToEndGame();
        for (ItemStack itemStack : event.getEntity().getInventory()) {
            if(itemStack != null) {
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
            }
        }
        makePlayerSpectator(event.getEntity());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        livingPlayerList.remove(event.getPlayer());
        checkToEndGame();
        makePlayerSpectator(event.getPlayer());
    }

    private void checkToEndGame() {
        if(livingPlayerList.size() <= 1) {
            HardcoreGamesMain.getGame().end(livingPlayerList.get(0));
        }
    }

    public static void makePlayerSpectator(Player player) {
        livingPlayerList.remove(player);
        for (ItemStack itemStack : player.getInventory()) {
            if(itemStack != null) {
                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            }
        }
        player.getInventory().clear();
        Bukkit.getOnlinePlayers().forEach(player1 -> player1.hidePlayer(HardcoreGamesMain.getPlugin(), player));
        player.setAllowFlight(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if(Item.undroppableItemList.contains(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
        if(!livingPlayerList.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerAttemptPickupItem(PlayerAttemptPickupItemEvent event) {
        if(!livingPlayerList.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!livingPlayerList.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            if(!livingPlayerList.contains((Player) event.getEntity())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            if (!livingPlayerList.contains((Player) event.getDamager())) {
                event.setCancelled(true);
            }
        }
    }
}
