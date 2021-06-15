package de.ahrl.hardcoregames.utility;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommand extends Command {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            onExecute(player, args);
        } else {
            sender.sendMessage(ChatColor.RED + "You have to be a Player to use this Command!");
        }
        return false;
    }

    public void onExecute(Player player, String[] args) {
    }
}
