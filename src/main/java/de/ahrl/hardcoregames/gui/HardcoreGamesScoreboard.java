package de.ahrl.hardcoregames.gui;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.utility.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class HardcoreGamesScoreboard {
    public static Scoreboard createHardcoreGamesScoreboard() {
        Scoreboard scoreboard = HardcoreGamesMain.getPlugin().getServer().getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("HardcoreGames", "Dummy", ChatColor.WHITE.toString()
                + ChatColor.BOLD + "H"
                + ChatColor.AQUA + "ARDCORE"
                + ChatColor.WHITE + ChatColor.BOLD + "G"
                + ChatColor.AQUA + "AMES");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore(ChatColor.BLUE.toString() + ChatColor.BOLD + "Kit:").setScore(7);
        Team kit = scoreboard.registerNewTeam("kit");
        kit.addEntry("§1");
        objective.getScore("§1").setScore(6);
        objective.getScore(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Time:").setScore(5);
        Team time = scoreboard.registerNewTeam("time");
        time.addEntry("§2");
        objective.getScore("§2").setScore(4);
        objective.getScore(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Player:").setScore(3);
        Team player = scoreboard.registerNewTeam("player");
        player.addEntry("§3");
        objective.getScore("§3").setScore(2);
        objective.getScore(ChatColor.GREEN.toString() + ChatColor.BOLD + "CPS:").setScore(1);
        Team cps = scoreboard.registerNewTeam("cps");
        cps.addEntry("§4");
        objective.getScore("§4").setScore(0);
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.getScoreboard().getTeam("player").setPrefix(ChatColor.GRAY + " " + Bukkit.getOnlinePlayers().size());
                }
            }
        }.runTaskTimer(HardcoreGamesMain.getPlugin(), 0, 1);
        return scoreboard;
    }

    public static void updateKitInScoreboardForPlayer(Player player) {
        if(Kit.kitUserList.containsKey(player)) {
            player.getScoreboard().getTeam("kit").setPrefix(ChatColor.GRAY + " - " + Kit.kitUserList.get(player).getName());
        }
    }

    public static void updateTimeInScoreboard(int time, boolean subtractMode) {
        if(subtractMode) {
            new BukkitRunnable() {
                int t = time;
                @Override
                public void run() {
                    if(t != 0) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.getScoreboard().getTeam("time").setPrefix(ChatColor.GRAY + " " + HardcoreGamesMain.manageTime(t));
                        }
                        t--;
                    }
                }
            }.runTaskTimer(HardcoreGamesMain.getPlugin(), 0, 20);
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.getScoreboard().getTeam("time").setPrefix(ChatColor.GRAY + " " + HardcoreGamesMain.manageTime(time));
            }
        }
    }

    public static void updateCPSInScoreboardForPlayer(Player player, int cps) {
        player.getScoreboard().getTeam("cps").setPrefix(ChatColor.GRAY + " " + cps + " CPS");
    }
}
