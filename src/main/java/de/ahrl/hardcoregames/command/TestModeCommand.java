package de.ahrl.hardcoregames.command;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.resource.SpectateSystem;
import de.ahrl.hardcoregames.utility.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SpectateSystem.livingPlayerList.addAll(Bukkit.getOnlinePlayers());
        HardcoreGamesMain.setGameStatus(GameStatus.TESTMODE);
        HardcoreGamesMain.getGame().generateWinPlatform((Player) sender);
        return false;
    }
}
