package de.ahrl.hardcoregames.resource;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.utility.GameStatus;
import de.ahrl.hardcoregames.utility.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class KitChecker implements Listener {
    public static boolean checkKit(Player player, Kit kit) {
        if (HardcoreGamesMain.getGameStatus().equals(GameStatus.INGAME) || HardcoreGamesMain.getGameStatus().equals(GameStatus.TESTMODE)) {
            if (kit.hasUser(player)) {
                if (kit.hasKitItem()) {
                    if (player.getInventory().getItemInMainHand().equals(kit.getKitItem())) {
                        return !kit.hasCooldown(player);
                    }
                } else {
                    return !kit.hasCooldown(player);
                }
            }
        }
        return false;
    }
}
