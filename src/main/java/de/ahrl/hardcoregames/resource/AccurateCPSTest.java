package de.ahrl.hardcoregames.resource;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.gui.HardcoreGamesScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class AccurateCPSTest implements Listener {
    HashMap<Player, Integer> cps = new HashMap<>();
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!event.getAction().equals(Action.PHYSICAL)) {
            if(event.getHand().equals(EquipmentSlot.HAND)) {
                if(cps.containsKey(player)) {
                    cps.put(player, cps.get(player) + 1);
                } else {
                    cps.put(player, 1);
                }
                HardcoreGamesScoreboard.updateCPSInScoreboardForPlayer(player, cps.get(player));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        cps.put(player, cps.get(player) - 1);
                        HardcoreGamesScoreboard.updateCPSInScoreboardForPlayer(player, cps.get(player));
                    }
                }.runTaskLater(HardcoreGamesMain.getPlugin(), 20);
            }
        }
    }
}
