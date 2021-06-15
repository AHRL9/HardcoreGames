package de.ahrl.hardcoregames.kit;

import de.ahrl.hardcoregames.resource.KitChecker;
import de.ahrl.hardcoregames.utility.Kit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlumenMaedchen implements Listener {
    Kit blumenmaedchen = new Kit("Blumenmaedchen", "You can eat flowers to get the power of them and you can wither your enemies away!", 60, Material.CORNFLOWER).
            setKitItem(Material.WITHER_ROSE, "Evil Flower", "The fulfillment of the flowers!");

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            LivingEntity entity = (LivingEntity) event.getEntity();
            if(KitChecker.checkKit(player, blumenmaedchen)) {
                event.setDamage(4);
                entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 75, 1));
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (event.getItem() == null) {
                return;
            }
            if(event.getItem().getType().equals(Material.CORNFLOWER)) {
                AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                double maxHealth = maxHealthAttribute.getValue();
                double health = player.getHealth();
                double heal = 3.0D;
                if (health != maxHealth) {
                    if (health >= maxHealth - heal) {
                        player.setHealth(maxHealth);
                    } else {
                        player.setHealth(health + heal);
                    }
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(blumenmaedchen.hasUser(player)) {
            if(event.getBlock().getType() == Material.DANDELION
                    || event.getBlock().getType() == Material.POPPY
                    || event.getBlock().getType() == Material.BLUE_ORCHID
                    || event.getBlock().getType() == Material.ALLIUM
                    || event.getBlock().getType() == Material.AZURE_BLUET
                    || event.getBlock().getType() == Material.RED_TULIP
                    || event.getBlock().getType() == Material.ORANGE_TULIP
                    || event.getBlock().getType() == Material.WHITE_TULIP
                    || event.getBlock().getType() == Material.PINK_TULIP
                    || event.getBlock().getType() == Material.OXEYE_DAISY
                    || event.getBlock().getType() == Material.LILY_OF_THE_VALLEY) {
                event.setDropItems(false);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.CORNFLOWER));
            }
        }
    }
}
