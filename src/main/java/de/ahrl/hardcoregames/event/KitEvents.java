package de.ahrl.hardcoregames.event;

import de.ahrl.hardcoregames.player.HardcoreGamesPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

public abstract class KitEvents {
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event, HardcoreGamesPlayer attacker, LivingEntity entity) {
    }
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event, HardcoreGamesPlayer player, LivingEntity attacker) {
    }
    public void onPlayerRightClickKitItem(PlayerInteractEvent event, HardcoreGamesPlayer player, ItemStack item) {
    }
    public void onPlayerLeftClickKitItem(PlayerInteractEvent event, HardcoreGamesPlayer player, ItemStack item) {
    }
    public void onPlayerRightClickEntityKitItem(PlayerInteractAtEntityEvent event, HardcoreGamesPlayer player, ItemStack item) {
    }
    public void onPlayerDamageEntityWithKitItem(EntityDamageByEntityEvent event, HardcoreGamesPlayer player, LivingEntity entity) {
    }
    public void onPlayerKillEntity(EntityDeathEvent event, HardcoreGamesPlayer killer, LivingEntity entity) {
    }
    public void onPlayerKillPlayer(PlayerDeathEvent event, HardcoreGamesPlayer killer, HardcoreGamesPlayer player) {
    }
    public void onPlayerSneak(PlayerToggleSneakEvent event, HardcoreGamesPlayer player){
    }
    public void onPlayerUnsneak(PlayerToggleSneakEvent event, HardcoreGamesPlayer player){
    }
    public void onPlayerDeath(PlayerDeathEvent event, HardcoreGamesPlayer player) {
    }
    public void onPlayerDamageEntityWithProjectile(ProjectileHitEvent event, HardcoreGamesPlayer attacker, LivingEntity entity) {
    }
}
