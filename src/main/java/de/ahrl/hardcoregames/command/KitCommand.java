package de.ahrl.hardcoregames.command;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.gui.HardcoreGamesScoreboard;
import de.ahrl.hardcoregames.gui.KitSelector;
import de.ahrl.hardcoregames.utility.GameStatus;
import de.ahrl.hardcoregames.utility.Kit;
import de.ahrl.hardcoregames.utility.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitCommand extends PlayerCommand implements TabCompleter {
    @Override
    public void onExecute(Player player, String[] args) {
        if(HardcoreGamesMain.getGameStatus().equals(GameStatus.PENDING) || !Kit.kitUserList.containsKey(player) || HardcoreGamesMain.getGameStatus().equals(GameStatus.TESTMODE)) {
            if(args.length >= 1) {
                for (Kit kit : Kit.kitList) {
                    if(args[0].equalsIgnoreCase(kit.getId())) {
                        if(args.length >= 2) {
                            if (args[1].equalsIgnoreCase("info")) {
                                player.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + kit.getName() + ChatColor.RESET + ChatColor.GRAY + " [" + kit.getId() + "]");
                                player.sendMessage(" ");
                                player.sendMessage(" - Description: ");
                                player.sendMessage(kit.getDescription());
                                player.sendMessage(" ");
                                player.sendMessage(" - Cooldown: " + kit.getCooldown() + " seconds");
                            }
                        } else {
                            kit.addToPlayer(player);
                            HardcoreGamesScoreboard.updateKitInScoreboardForPlayer(player);
                        }
                    }
                }
            } else {
                KitSelector.createKitSelector(player);
            }
        } else {
            player.sendMessage(ChatColor.RED + "Game already started!");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> completerList = new ArrayList<>();
        if(args.length == 1) {
            for (Kit kit : Kit.kitList) {
                completerList.add(kit.getId());
            }
        }
        if(args.length == 2) {
            completerList.add("info");
        }
        return completerList;
    }
}
