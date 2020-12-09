package instruments;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InstrumentInventory implements InventoryHolder {

    private Inventory inv;
    private InstrumentType instrumentType;

    public InstrumentInventory(InstrumentType instrumentType) {
        inv = Bukkit.createInventory(this, 54, "Instruments: " + instrumentType.toString());
        this.instrumentType = instrumentType;
    }

    public void display(Player player) {
        this.fillBackground();
        this.fillKeys();

        this.inv.setItem(0, this.createInstrumentIcon());

        player.openInventory(this.inv);
    }

    private void fillBackground() {
        ItemStack backgroundTile = this.createTile(Material.BROWN_STAINED_GLASS_PANE, " ");
        for(int i = 0; i < 54; i++) {
            inv.setItem(i, backgroundTile);
        }
    }

    private void fillKeys() {
        String[] naturalNotes = new String[]{
                "G", "A", "B", "C", "D", "E", "F"
        };

        String[] sharpNotes = new String[]{
                "F#", "G#", "A#", "C#", "D#"
        };

        // Sets white keys
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 7; j++) {
                int base;
                if(i == 0) {
                    base = 19;
                } else {
                    base = 46;
                }

                inv.setItem(base + j, this.createTile(Material.WHITE_STAINED_GLASS_PANE, ChatColor.GREEN + naturalNotes[j]));
            }
        }

        // Sets black keys
        for(int i = 0; i < 2; i++) {
            int keyIndex = 0;
            for(int j = 0; j < 6; j++) {
                int base;
                if(i == 0) {
                    base = 10;
                } else {
                    base = 37;
                }

                if(j != 3) {
                    inv.setItem(base + j, this.createTile(Material.BLACK_STAINED_GLASS_PANE, ChatColor.DARK_GREEN + sharpNotes[keyIndex]));
                    keyIndex++;
                }
            }
        }
    }

    private ItemStack createTile(Material material, String name) {
        ItemStack tile = new ItemStack(material, 1);
        ItemMeta itemMeta = tile.getItemMeta();
        itemMeta.setDisplayName(name);
        tile.setItemMeta(itemMeta);
        return tile;
    }

    private ItemStack createInstrumentIcon() {
        ItemStack icon = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta itemMeta = icon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + this.instrumentType.toString());
        icon.setItemMeta(itemMeta);
        return icon;
    }

    @Override
    public Inventory getInventory() {
        return this.inv;
    }
}
