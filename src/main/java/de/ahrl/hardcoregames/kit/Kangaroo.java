package de.ahrl.hardcoregames.kit;

import de.ahrl.hardcoregames.resource.KitChecker;
import de.ahrl.hardcoregames.utility.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Kangaroo implements Listener {
    Kit kangaroo = new Kit("Kangaroo", "Jump away like a Kangaroo!", 0, Material.FIREWORK_ROCKET).
            setKitItem(Material.FIREWORK_ROCKET, "Jump", "Rightclick to jump!");

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(KitChecker.checkKit(player, kangaroo)) {
            event.setCancelled(true);
            if(player.isOnGround() && !player.isInWater()) {
                Location jumpLocation = player.getLocation();
                jumpLocation.setPitch(0);
                if(player.getDisplayName().equals("xXLJoseph")) {
                    player.sendMessage(ChatColor.RED + "Böööser Leon!");
                    player.setVelocity(jumpLocation.getDirection().multiply(0.5).add(new Vector(0, 100, 0)));
                } else if(player.isSneaking()) {
                    player.setVelocity(jumpLocation.getDirection().multiply(1.25).add(new Vector(0, 0.5, 0)) );
                } else {
                    player.setVelocity(jumpLocation.getDirection().multiply(0.75).add(new Vector(0, 0.75, 0)) );
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (kangaroo.hasUser(player)) {
                if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    event.setDamage(event.getDamage() / 2);
                }
            }
        }
    }
}
