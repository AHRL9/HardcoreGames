package de.ahrl.hardcoregames.kit;

import de.ahrl.hardcoregames.resource.KitChecker;
import de.ahrl.hardcoregames.utility.Kit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Samurai implements Listener {
    Kit samurai = new Kit("Samurai", "Use a Sword to snap of the Head from your enemie!", 15, Material.IRON_SWORD);

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (KitChecker.checkKit(player, samurai)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(player.getInventory().getItemInMainHand().toString().contains("SWORD")) {
                    if(player.isSneaking()) {
                        player.setVelocity(player.getEyeLocation().getDirection().multiply(-1).setY(0.25));
                        samurai.addCooldown(player);
                    } else if(playerIsLookingAtLivingEntity(player)) {
                        player.setVelocity(player.getEyeLocation().getDirection().multiply(2));
                        LivingEntity livingEntity = (LivingEntity) getEntityPlayerIsLookingAt(player);
                        livingEntity.damage(10, player);
                        livingEntity.getWorld().strikeLightningEffect(livingEntity.getLocation());
                        samurai.addCooldown(player);
                    }
                }
            }
        }
    }

    private boolean playerIsLookingAtLivingEntity(Player player) {
        return getEntityPlayerIsLookingAt(player) != null;
    }

    private boolean isPlayerLookingAtThisLivingEntity(Player player, LivingEntity livingEntity) {
        Location eyeLocation = player.getEyeLocation();
        Vector toEntity = livingEntity.getEyeLocation().toVector().subtract(eyeLocation.toVector());
        double dot = toEntity.normalize().dot(eyeLocation.getDirection());
        return dot > 0.99;
    }

    private Entity getEntityPlayerIsLookingAt(Player player) {
        Entity entityPlayerIsLookingAt = null;
        for (Entity entity : player.getNearbyEntities(8, 8, 8)) {
            if(entity instanceof LivingEntity) {
                if(isPlayerLookingAtThisLivingEntity(player, (LivingEntity) entity)) {
                    entityPlayerIsLookingAt = entity;
                }
            }
        }
        return entityPlayerIsLookingAt;
    }
}
