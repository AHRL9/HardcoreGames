package de.ahrl.hardcoregames.kit;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.resource.KitChecker;
import de.ahrl.hardcoregames.utility.Kit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Cat implements Listener {
    private final Kit cat = new Kit("Cat", "Scratch your enemies!", 15, Material.RABBIT_FOOT).
            setKitItem(Material.RABBIT_FOOT, "Cat's Claw", "A Claw that hurts enemies!");

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof LivingEntity) {
            Player player = event.getPlayer();
            LivingEntity entity = (LivingEntity) event.getRightClicked();
            if (event.getHand().equals(EquipmentSlot.HAND)) {
                if (KitChecker.checkKit(player, cat)) {
                    randomScratch(player, entity);
                    cat.addCooldown(player);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (cat.hasUser(player)) {
                if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    if (player.isSneaking()) {
                        event.setDamage(1);
                    }
                }
            }
        }
    }

    private void randomScratch(Player player, LivingEntity livingEntity) {
        new BukkitRunnable() {
            int t = 0;

            @Override
            public void run() {
                Random random = new Random();
                int hitCount = random.nextInt(3) + 1;
                if (hitCount <= t) {
                    cancel();
                }
                livingEntity.getWorld().spawnParticle(Particle.SWEEP_ATTACK, livingEntity.getLocation().add(0, 1, 0), 1);
                livingEntity.damage(livingEntity.getHealth() / 2, player);
                t++;
            }
        }.runTaskTimer(HardcoreGamesMain.getPlugin(), 0, 10);
    }
}
