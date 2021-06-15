package de.ahrl.hardcoregames.utility;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return false;
    }

    public void onExecute(CommandSender sender, String[] args) {
    }
}
