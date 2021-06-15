package de.ahrl.hardcoregames.kit;

import de.ahrl.hardcoregames.resource.KitChecker;
import de.ahrl.hardcoregames.resource.LastHittedEntity;
import de.ahrl.hardcoregames.utility.Kit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Ninja implements Listener {
    private final Kit ninja = new Kit("Ninja", "Surprise your enemies from behind!", 15, Material.INK_SAC);

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (event.isSneaking()) {
            if (KitChecker.checkKit(player, ninja)) {
                LivingEntity entity = LastHittedEntity.getLastHittedEntity(player);
                if (LastHittedEntity.hasLastHittedEntity(player)) {
                    player.teleport(calculateNinjaBehind(entity));
                    player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 8);
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1, 1);
                    ninja.addCooldown(player);
                    LastHittedEntity.resetLastHittedEntity(player);
                }
            }
        }
    }

    private Location calculateNinjaBehind(LivingEntity entity) {
        float nang = entity.getLocation().getYaw() + 90;
        if (nang < 0) {
            nang += 360;
        }
        double nX = Math.cos(Math.toRadians(nang));
        double nZ = Math.sin(Math.toRadians(nang));
        return entity.getLocation().clone().subtract(nX, 0, nZ);
    }
}
