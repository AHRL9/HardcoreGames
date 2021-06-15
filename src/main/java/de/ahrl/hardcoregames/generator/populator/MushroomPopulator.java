package de.ahrl.hardcoregames.generator.populator;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class MushroomPopulator extends BlockPopulator {
    @Override
    public void populate(World world, Random random, Chunk source) {
        for (int i = 0; i < 8; i++) {
            int x = random.nextInt(15);
            int z = random.nextInt(15);
            for (int y = 128; y > 0; y--) {
                if(source.getBlock(x, y, z).getType().equals(Material.GRASS_BLOCK)) {
                    if(source.getBlock(x, y + 1, z).getType().equals(Material.AIR)) {
                        if(random.nextBoolean()) {
                            source.getBlock(x, y + 1, z).setType(Material.RED_MUSHROOM);
                            //generateMushroomSwarm(random, source, x, y + 1, z, Material.RED_MUSHROOM, 8, 2);
                        } else {
                            source.getBlock(x, y + 1, z).setType(Material.BROWN_MUSHROOM);
                            //generateMushroomSwarm(random, source, x, y + 1, z, Material.BROWN_MUSHROOM, 8, 2);
                        }
                    }
                }
            }
        }
    }

    private void generateMushroomSwarm(Random random, Chunk chunk, int x, int y, int z, Material mushroomType, int maxSize, int randomSizeRange) {
        chunk.getBlock(x, y, z).setType(mushroomType);
        int newX = x;
        int newZ = z;
        for (int amount = 0; amount <= maxSize - random.nextInt(randomSizeRange); amount++) {
            if(random.nextBoolean()) {
                newX ++;
            }
            if(random.nextBoolean()) {
                newZ ++;
            }
            if(!(newX >= 16 || newZ >= 16)) {
                if(chunk.getBlock(newX, y - 1, newZ).getType().equals(Material.GRASS_BLOCK)) {
                    if(chunk.getBlock(newX, y, newZ).getType().equals(Material.AIR)) {
                        chunk.getBlock(newX, y, newZ).setType(mushroomType);
                    }
                }
            }
        }
    }
}
