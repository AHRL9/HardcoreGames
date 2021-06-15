package de.ahrl.hardcoregames.command;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        HardcoreGamesMain.getGame().start();
        return false;
    }
}
