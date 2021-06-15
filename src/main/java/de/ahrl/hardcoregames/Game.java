package de.ahrl.hardcoregames;

import com.destroystokyo.paper.Title;
import de.ahrl.hardcoregames.gui.HardcoreGamesScoreboard;
import de.ahrl.hardcoregames.resource.SpectateSystem;
import de.ahrl.hardcoregames.utility.GameStatus;
import de.ahrl.hardcoregames.utility.Kit;
import de.ahrl.hardcoregames.utility.Tracker;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Game {
    private final int pendingTime;
    private final int protectionTime;
    private final int maxGameTime;
    private final int afterGameTime;
    private int time;

    public Game(int pendingTime, int protectionTime, int maxGameTime, int afterGameTime) {
        this.pendingTime = pendingTime;
        this.protectionTime = protectionTime;
        this.maxGameTime = maxGameTime;
        this.afterGameTime = afterGameTime;
    }

    public void start() {
        HardcoreGamesMain.setGameStatus(GameStatus.PENDING);
        HardcoreGamesScoreboard.updateTimeInScoreboard(pendingTime, true);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(Tracker::giveCompassToPlayer);
                SpectateSystem.livingPlayerList.addAll(Bukkit.getOnlinePlayers());
                Random random = new Random();
                for (Kit kit : Kit.kitList) {
                    if(kit.getId().equals("surprise")) {
                        for (Player player : kit.getUserList()) {
                            Kit.kitList.get(random.nextInt(Kit.kitList.size())).addToPlayer(player);
                        }
                    }
                    if(kit.hasKitItem()) {
                        for (Player player : kit.getUserList()) {
                            kit.addKitItemToPlayer(player);
                        }
                    }
                }
                HardcoreGamesMain.setGameStatus(GameStatus.PROTECTION);
                time = 0;
                HardcoreGamesScoreboard.updateTimeInScoreboard(protectionTime, true);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        HardcoreGamesMain.setGameStatus(GameStatus.INGAME);
                    }
                }.runTaskLater(HardcoreGamesMain.getPlugin(), protectionTime * 20L);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(HardcoreGamesMain.getGameStatus().equals(GameStatus.INGAME)) {
                            HardcoreGamesScoreboard.updateTimeInScoreboard(time, false);
                        }
                        if(time >= maxGameTime) {
                            pit();
                        }
                        time++;
                    }
                }.runTaskTimer(HardcoreGamesMain.getPlugin(), 0, 20);
            }
        }.runTaskLater(HardcoreGamesMain.getPlugin(), pendingTime * 20L);
    }

    public void end(Player winner) {
        HardcoreGamesMain.setGameStatus(GameStatus.ENDED);
        generateWinPlatform(winner);
        Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle(
                new Title(ChatColor.AQUA.toString() + ChatColor.BOLD + winner.getDisplayName() + ChatColor.RESET + ChatColor.GRAY + " is the winner!")));
        /*new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(ChatColor.RED + "Server is restarting!"));
            }
        }.runTaskLater(HardcoreGamesMain.getPlugin(), afterGameTime * 20L);*/
    }

    public void generateWinPlatform(Player winner) {
        World world = winner.getWorld();
        Random random = new Random(world.getSeed());
        Location winLocation = winner.getLocation();
        winLocation.setY(192);
        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                world.getBlockAt(x + winner.getLocation().getBlockX() - 2, 192, z + winner.getLocation().getBlockZ() - 2).setType(Material.CAKE);
            }
        }
        winner.teleport(winLocation.add(0, 2, 0));
        for (int i = 0; i < 4; i++) {
            Firework firework = (Firework) world.spawnEntity(new Location
                            (world , random.nextInt(5) - 2.5 + winLocation.getBlockX(), 192 + random.nextInt(5), random.nextInt(5) - 2.5 + winLocation.getBlockZ()),
                    EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.setPower(1);
            if (random.nextBoolean()) {
                fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.RED).withFlicker().withTrail().build());
            } else {
                fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.WHITE).withFlicker().withTrail().build());
            }
            firework.setFireworkMeta(fireworkMeta);
            firework.detonate();
        }
    }

    public void pit() {

    }
}
