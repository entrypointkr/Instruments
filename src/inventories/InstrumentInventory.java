package inventories;

import instruments.Instruments;
import instruments.Scale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import instruments.InstrumentType;

import java.util.ArrayList;

public class InstrumentInventory implements InventoryHolder {

    private Inventory inv;
    private InstrumentType instrumentType;
    private Instruments instance = Instruments.getInstance();
    private boolean chordsEnabled;

    public InstrumentInventory(InstrumentType instrumentType) {
        inv = Bukkit.createInventory(this, 54, "Instruments: " + instrumentType.toString());
        this.instrumentType = instrumentType;
    }

    public void display(Player player) {
        if(!instance.getInstrumentManager().containsKey(player)) return;

        instance.getInstrumentManager().get(player).setTransitioning(true);

        this.fillBackground();
        this.fillKeys();

        this.inv.setItem(0, this.createInstrumentIcon());
        this.inv.setItem(7, this.createChordsIcon());
        this.inv.setItem(8, this.createScalesIcon());

        player.openInventory(this.inv);

        instance.getInstrumentManager().get(player).setTransitioning(false);
    }

    public void displayHotbar(Player player, Scale scale) {
        Inventory playerInv = player.getInventory();

        ArrayList<String> notes = scale.getNotes();
        ArrayList<String> missingNotes = scale.getMissingNotes();

        for(int i = 0; i < notes.size(); i++) {
            playerInv.setItem(i, this.createHotBarInstrument(notes.get(i)));
        }

        for(int i = 0; i < missingNotes.size(); i++) {
            playerInv.setItem(i + 9, this.createHotBarInstrument(missingNotes.get(i)));
        }
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

    private ItemStack createScalesIcon() {
        ItemStack icon = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = icon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Scales");
        icon.setItemMeta(itemMeta);
        return icon;
    }

    private ItemStack createChordsIcon() {
        Material mat = Material.LIME_STAINED_GLASS_PANE;
        if(!this.chordsEnabled) mat = Material.RED_STAINED_GLASS_PANE;

        ItemStack icon = new ItemStack(mat, 1);
        ItemMeta itemMeta = icon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Chords");
        icon.setItemMeta(itemMeta);
        return icon;
    }

    private ItemStack createHotBarInstrument(String name) {
        Material instrumentMaterial = this.instrumentType.getMaterial();
        ItemStack hotBarInstrument = new ItemStack(instrumentMaterial, 1);
        ItemMeta itemMeta = hotBarInstrument.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + name);
        hotBarInstrument.setItemMeta(itemMeta);
        return hotBarInstrument;
    }

    public void toggleChords() {
        this.chordsEnabled = !this.chordsEnabled;
    }

    @Override
    public Inventory getInventory() {
        return this.inv;
    }
}
