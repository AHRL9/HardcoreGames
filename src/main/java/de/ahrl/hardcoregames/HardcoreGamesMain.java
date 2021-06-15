package de.ahrl.hardcoregames;

import de.ahrl.hardcoregames.command.KitCommand;
import de.ahrl.hardcoregames.command.StartCommand;
import de.ahrl.hardcoregames.command.TestModeCommand;
import de.ahrl.hardcoregames.command.WorldCommand;
import de.ahrl.hardcoregames.gui.KitSelector;
import de.ahrl.hardcoregames.kit.*;
import de.ahrl.hardcoregames.resource.AccurateCPSTest;
import de.ahrl.hardcoregames.resource.JoinQuitListener;
import de.ahrl.hardcoregames.resource.LastHittedEntity;
import de.ahrl.hardcoregames.resource.SpectateSystem;
import de.ahrl.hardcoregames.utility.GameStatus;
import de.ahrl.hardcoregames.utility.Kit;
import de.ahrl.hardcoregames.utility.Tracker;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class HardcoreGamesMain extends JavaPlugin implements Listener {
    private static final Game game = new Game(60, 60 * 2, 60 * 60, 30);
    private static GameStatus gameStatus = GameStatus.NONE;
    private static HardcoreGamesMain plugin;

    @Override
    public void onEnable() {
        Kit surprise = new Kit("Surprise", "You get a random kit!", 0, Material.CAKE);
        Soup.setSoupHealAmount(7.0D);
        registerCommands();
        registerEvents();
        displayActionBar();
        plugin = this;
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands() {
        PluginCommand kit = getCommand("kit");
        kit.setExecutor(new KitCommand());
        kit.setTabCompleter(new KitCommand());
        getCommand("start").setExecutor(new StartCommand());
        getCommand("testmode").setExecutor(new TestModeCommand());
        getCommand("world").setExecutor(new WorldCommand());
    }

    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(this, this);
        pluginManager.registerEvents(new AccurateCPSTest(), this);
        pluginManager.registerEvents(new BlumenMaedchen(), this);
        pluginManager.registerEvents(new Tracker(), this);
        pluginManager.registerEvents(new SpectateSystem(), this);
        pluginManager.registerEvents(new Gladiator(), this);
        pluginManager.registerEvents(new Soup(), this);
        pluginManager.registerEvents(new LastHittedEntity(), this);
        pluginManager.registerEvents(new JoinQuitListener(), this);
        pluginManager.registerEvents(new Cat(), this);
        pluginManager.registerEvents(new Samurai(), this);
        pluginManager.registerEvents(new Ninja(), this);
        pluginManager.registerEvents(new Blink(), this);
        pluginManager.registerEvents(new Kangaroo(), this);
        pluginManager.registerEvents(new KitSelector(), this);
    }

    public static HardcoreGamesMain getPlugin() {
        return plugin;
    }

    private void displayActionBar() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Kit.kitUserList.keySet()) {
                    Kit kit = Kit.kitUserList.get(player);
                    if (kit.hasCooldown(player)) {
                        int cooldown = kit.getCooldown();
                        String cooldownString = StringUtils.repeat("|", cooldown);
                        int remainingCooldown = kit.getCooldownFromPlayer(player);
                        String remainingCoolDownString = cooldownString.substring(0, remainingCooldown);
                        String finishedCoolDownString = cooldownString.substring(remainingCooldown);
                        player.sendActionBar(ChatColor.GRAY + "Cooldown: " + ChatColor.AQUA + ChatColor.BOLD + remainingCoolDownString + ChatColor.GRAY + ChatColor.BOLD + finishedCoolDownString);
                    } else {
                        player.sendActionBar(" ");
                    }
                }
            }
        }.runTaskTimer(this, 0, 1);
    }

    public static Game getGame() {
        return game;
    }

    public static GameStatus getGameStatus() {
        return gameStatus;
    }

    public static void setGameStatus(GameStatus gameStatus) {
        HardcoreGamesMain.gameStatus = gameStatus;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(getGameStatus().equals(GameStatus.PENDING) || getGameStatus().equals(GameStatus.ENDED)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent event) {
        if(getGameStatus().equals(GameStatus.PENDING) || getGameStatus().equals(GameStatus.ENDED)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            if(!(getGameStatus().equals(GameStatus.INGAME) || getGameStatus().equals(GameStatus.TESTMODE))) {
                event.setCancelled(true);
            }
        }
    }

    public static String manageTime(int duration) {
        String string = "";
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        if (duration / 60 / 60 / 24 >= 1) {
            days = duration / 60 / 60 / 24;
            duration -= duration / 60 / 60 / 24 * 60 * 60 * 24;
        }
        if (duration / 60 / 60 >= 1) {
            hours = duration / 60 / 60;
            duration -= duration / 60 / 60 * 60 * 60;
        }
        if (duration / 60 >= 1) {
            minutes = duration / 60;
            duration -= duration / 60 * 60;
        }
        if (duration >= 1) {
            seconds = duration;
        }
        if(days != 0) {
            if(days == 1) {
                string = string + days + " day and ";
            } else {
                string = string + days + " days and ";
            }
        }
        if (hours <= 9) {
            string = string + "0" + hours + ":";
        } else {
            string = string + hours + ":";
        }
        if (minutes <= 9) {
            string = string + "0" + minutes + ":";
        } else {
            string = string + minutes + ":";
        }
        if (seconds <= 9) {
            string = string + "0" + seconds;
        } else {
            string = string + seconds;
        }
        return string;
    }
}
