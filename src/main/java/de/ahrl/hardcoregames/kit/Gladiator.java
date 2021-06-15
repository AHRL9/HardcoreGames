package de.ahrl.hardcoregames.kit;

import de.ahrl.hardcoregames.resource.KitChecker;
import de.ahrl.hardcoregames.utility.Kit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Gladiator implements Listener {
    Kit gladiator = new Kit("Gladiator", "Catch your enemies in a glass box and 1v1 them!", 90, Material.IRON_BARS).
            setKitItem(Material.IRON_BARS, "Cage", "Right Click your Enemies to catch them!");
    HashMap<Player, LivingEntity> gladiatorPairList = new HashMap<>();
    HashMap<Player, Location> gladiatorLocation = new HashMap<>();
    HashMap<LivingEntity, Location> gotGladiatedLocation = new HashMap<>();
    HashMap<Player, ArrayList<Block>> gladiatorArena = new HashMap<>();

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getRightClicked();
            Player player = event.getPlayer();
            if(KitChecker.checkKit(player, gladiator)) {
                startGladiator(16, 24, player, entity);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        for(ArrayList<Block> blocks : gladiatorArena.values()) {
            if(blocks.contains(event.getBlock())) {
                if(gladiatorPairList.containsKey(player) || gladiatorPairList.containsValue(player)) {
                    if(gladiatorPairList.containsKey(player)) {
                        gladiatorPairList.remove(player);
                        destroyArena(player);
                    }
                    if(gladiatorPairList.containsValue(player)) {
                        for(Player gladiatedPlayer : gladiatorPairList.keySet()) {
                            if(gladiatorPairList.get(gladiatedPlayer).equals(player)) {
                                gladiatorPairList.remove(player);
                                destroyArena(gladiatedPlayer);
                            }
                        }
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(gladiatorPairList.containsKey(player)) {
            gladiatorPairList.remove(player);
            destroyArena(player);
        }
        if(gladiatorPairList.containsValue(player)) {
            for(Player gladiatedPlayer : gladiatorPairList.keySet()) {
                if(gladiatorPairList.get(gladiatedPlayer).equals(player)) {
                    gladiatorPairList.remove(player);
                    destroyArena(gladiatedPlayer);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntity() instanceof Player) {
            if (gladiatorPairList.containsKey((Player) event.getEntity())) {
                gladiatorPairList.remove((Player) event.getEntity());
                destroyArena((Player) event.getEntity());
            }
        }
        event.getEntity();
        if (gladiatorPairList.containsValue(event.getEntity())) {
            for (Player player : gladiatorPairList.keySet()) {
                if(gladiatorPairList.get(player).equals(event.getEntity())) {
                    gladiatorPairList.remove(player);
                    destroyArena(player);
                }
            }
        }
    }

    private void startGladiator(int size, int heigth, Player player, LivingEntity livingEntity) {
        ArrayList<Block> arenaBlocks = new ArrayList<>();
        for (int x = 0; x < size + 2; x++) {
            for (int z = 0; z < size + 2; z++) {
                for (int y = 0; y < (size / 2) + 2; y++) {
                    player.getWorld().getBlockAt(player.getLocation().add(x, y + (heigth - 1), z)).setType(Material.GLASS);
                    arenaBlocks.add(player.getLocation().add(x, y + (heigth - 1), z).getBlock());
                }
            }
        }
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                for (int y = 0; y < size / 2; y++) {
                    player.getWorld().getBlockAt(player.getLocation().add(x + 1, y + heigth, z + 1)).setType(Material.AIR);
                }
            }
        }
        gladiatorArena.put(player, arenaBlocks);
        gladiatorPairList.put(player, livingEntity);
        livingEntity.teleport(player.getLocation().add(size - 1, heigth + 1, size - 1));
        player.teleport(player.getLocation().add(2, heigth + 1, 2));
    }

    private void destroyArena(Player player) {
        for(Player gladiator : gladiatorPairList.keySet()) {
            if(player.equals(gladiator)) {
                gladiator.teleport(gladiatorLocation.get(gladiator));
                gladiatorPairList.get(gladiator).teleport(gotGladiatedLocation.get(gladiatorPairList.get(gladiator)));
            }
        }
        for (Block block : gladiatorArena.get(player)) {
            block.getWorld().spawnParticle(Particle.CRIT, block.getLocation(), 4);
            block.setType(Material.AIR);
        }
    }
}
