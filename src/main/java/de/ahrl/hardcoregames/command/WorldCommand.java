package de.ahrl.hardcoregames.command;

import de.ahrl.hardcoregames.generator.MushroomWorldGeneration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        WorldCreator worldCreator = new WorldCreator(args[0]);
        worldCreator.generator(new MushroomWorldGeneration());
        worldCreator.createWorld();
        ((Player) sender).teleport(new Location(Bukkit.getWorld(args[0]), 0, 128, 0));
        return false;
    }
}
