package de.ahrl.hardcoregames.kit;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.resource.KitChecker;
import de.ahrl.hardcoregames.utility.Kit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Blink implements Listener {
    private final Kit blink = new Kit("Blink", "Teleport yourself!", 15, Material.NETHER_STAR).
            setKitItem(Material.NETHER_STAR, "Blink", "Wooosh... you got Teleported!");
    private int uses;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (KitChecker.checkKit(player, blink)) {
            if (uses >= 4) {
                blink.addCooldown(player);
                uses = 0;
            } else {
                player.teleport(player.getLocation().add(player.getLocation().getDirection().normalize().multiply(8)));
                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
                Location blinkBlocklocation = player.getLocation().subtract(0, 1, 0);
                Block blinkBlock = blinkBlocklocation.getBlock();
                if (blinkBlock.getType().equals(Material.AIR)) {
                    new BukkitRunnable() {
                        int blockState = 0;

                        @Override
                        public void run() {
                            if (blockState == 0) {
                                blinkBlock.setType(Material.LIME_STAINED_GLASS);
                            }
                            if (blockState == 1) {
                                blinkBlock.setType(Material.YELLOW_STAINED_GLASS);
                            }
                            if (blockState == 2) {
                                blinkBlock.setType(Material.ORANGE_STAINED_GLASS);
                            }
                            if (blockState == 3) {
                                blinkBlock.setType(Material.RED_STAINED_GLASS);
                            }
                            if (blockState == 4) {
                                blinkBlocklocation.getWorld().spawnParticle(Particle.CLOUD, blinkBlocklocation, 16);
                                blinkBlocklocation.getWorld().playSound(blinkBlocklocation, Sound.BLOCK_STONE_BREAK, 1, 1);
                                blinkBlock.setType(Material.AIR);
                                cancel();
                            }
                            blockState++;
                        }
                    }.runTaskTimer(HardcoreGamesMain.getPlugin(), 0, 20);
                }
                uses++;
                if (uses >= 4) {
                    blink.addCooldown(player);
                    uses = 0;
                }
            }
        }
    }
}
