package de.ahrl.hardcoregames.resource;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.gui.HardcoreGamesScoreboard;
import de.ahrl.hardcoregames.utility.GameStatus;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinQuitListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        maxHealthAttribute.setBaseValue(128);
        player.setScoreboard(HardcoreGamesScoreboard.createHardcoreGamesScoreboard());
        if(HardcoreGamesMain.getGameStatus().equals(GameStatus.INGAME) || HardcoreGamesMain.getGameStatus().equals(GameStatus.PROTECTION)) {
            SpectateSystem.makePlayerSpectator(player);
        }
    }
}
