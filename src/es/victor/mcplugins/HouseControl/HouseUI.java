package es.victor.mcplugins.HouseControl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class HouseUI implements Listener {
    private final ArrayList<String> houseData;
    private final Player player;
    private final Inventory inventory;

    public HouseUI(ArrayList<String> houseData, Player player) {
        this.houseData = houseData;
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 9, houseData.get(1));
    }

    public void mainWindow() {
        ItemStack default_house = new ItemStack(Material.DARK_OAK_DOOR, 1);

        default_house.getItemMeta().setDisplayName("Select house");
        default_house.getItemMeta().setLore(Arrays.asList("Change your default house"));
        default_house.setItemMeta(default_house.getItemMeta());

        this.inventory.addItem(default_house);

        this.player.openInventory(this.inventory);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() == this.inventory) {
            e.setCancelled(true);
            final ItemStack clickedItem = e.getCurrentItem();
            System.out.println("User has clicked");
            if (clickedItem.getItemMeta().getDisplayName() == "Close") {
                this.player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory() == this.inventory) {
            e.setCancelled(true);
        }
    }
}
