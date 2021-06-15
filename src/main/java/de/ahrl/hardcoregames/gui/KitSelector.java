package de.ahrl.hardcoregames.gui;

import de.ahrl.hardcoregames.HardcoreGamesMain;
import de.ahrl.hardcoregames.utility.Item;
import de.ahrl.hardcoregames.utility.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitSelector implements Listener {
    public static void createKitSelector(Player player) {
        int rowsize = 9;
        int size = rowsize * 6;
        Inventory kitSelector = HardcoreGamesMain.getPlugin().getServer().createInventory(null, size, "| Kit Selector |");

        ItemStack border = new Item(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").clearLore().create();
        ItemStack fill = new Item(Material.WHITE_STAINED_GLASS_PANE).setDisplayName(" ").clearLore().create();

        for (int i = 0; i < rowsize; i++) {
            kitSelector.setItem(i, border);
        }

        for (int i = rowsize; i < size - rowsize; i++) {
            kitSelector.setItem(i, fill);
        }

        for (int i = size - rowsize; i < size; i++) {
            kitSelector.setItem(i, border);
        }

        for (int i = 0; i < Kit.kitList.size(); i++) {
            kitSelector.setItem(rowsize + i, Kit.kitList.get(i).getKitSelectorItem());
        }
        player.openInventory(kitSelector);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("| Kit Selector |")) {
            if (item != null && item.getItemMeta() != null) {
                for (Kit kit : Kit.kitList) {
                    if (item.equals(kit.getKitSelectorItem())) {
                        kit.addToPlayer(player);
                        HardcoreGamesScoreboard.updateKitInScoreboardForPlayer(player);
                    }
                }
            }
            event.setCancelled(true);
        }
    }
}
