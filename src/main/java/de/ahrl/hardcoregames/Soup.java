package de.ahrl.hardcoregames;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Soup implements Listener {
    private static double soupHealAmount;

    public static void setSoupHealAmount(double soupHealAmount) {
        Soup.soupHealAmount = soupHealAmount;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (event.getItem() == null) {
                return;
            }
            if (event.getItem().getType().equals(Material.MUSHROOM_STEW)) {
                AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                double maxHealth = maxHealthAttribute.getValue();
                double health = player.getHealth();
                int foodLevel = player.getFoodLevel();
                if (health != maxHealth) {
                    if (health >= maxHealth - soupHealAmount) {
                        player.setHealth(maxHealth);
                    } else {
                        player.setHealth(health + soupHealAmount);
                    }
                    player.setFoodLevel(foodLevel + 1);
                    event.getItem().setType(Material.BOWL);
                } else if (foodLevel != 20) {
                    if (foodLevel >= 14) {
                        player.setFoodLevel(20);
                    } else {
                        player.setFoodLevel(foodLevel + 6);
                    }
                    event.getItem().setType(Material.BOWL);
                }
            }
        }
    }
}
