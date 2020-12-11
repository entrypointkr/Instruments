package instruments;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Utils {

    private static HashMap<Player, HashMap<Integer, ItemStack>> inventoryMap = new HashMap<>();

    public static void storeInventory(Player player) {
        Inventory inventory = player.getInventory();
        HashMap<Integer, ItemStack> items = new HashMap<>();
        for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) != null) {
                items.put(i, inventory.getItem(i));
            }
        }

        inventoryMap.put(player, items);
    }

    public static void loadInventory(Player player) {
        if(!inventoryMap.containsKey(player)) return;
        Inventory inventory = player.getInventory();
        HashMap<Integer, ItemStack> items = inventoryMap.get(player);
        for(Integer i : items.keySet()) {
            inventory.setItem(i, items.get(i));
        }

        inventoryMap.remove(player);
    }

}
