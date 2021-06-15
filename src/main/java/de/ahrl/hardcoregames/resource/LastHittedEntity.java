package de.ahrl.hardcoregames.resource;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class LastHittedEntity implements Listener {
    private static HashMap<Player, LivingEntity> lastHittedEntityList = new HashMap<>();

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();
            LivingEntity entity = (LivingEntity) event.getEntity();
            lastHittedEntityList.put(player, entity);
        }
    }

    public static LivingEntity getLastHittedEntity(Player player) {
        return lastHittedEntityList.get(player);
    }

    public static boolean hasLastHittedEntity(Player player) {
        return getLastHittedEntity(player) != null;
    }

    public static void resetLastHittedEntity(Player player) {
        if(hasLastHittedEntity(player)) {
            lastHittedEntityList.remove(player);
        }
    }
}
