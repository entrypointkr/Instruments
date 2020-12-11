package instruments.listeners;

import instruments.Instrument;
import instruments.Instruments;
import instruments.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    private Instruments instance = Instruments.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if(e.getClickedInventory() == null) return;

        if(!instance.getInstrumentManager().containsKey(p)) return;

        Instrument instrument = instance.getInstrumentManager().get(p);

        if(instrument.isHotBarMode()) {
            e.setCancelled(true);
            return;
        }

        if (e.getView().getTitle().contains("Instruments:")
                && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)
                && e.getView().getTopInventory().getType().equals(InventoryType.CHEST)) {
            e.setCancelled(true);

            org.bukkit.Instrument bukkitInstrument = org.bukkit.Instrument.valueOf(instrument.getInstrumentType().toString());

            // Player clicked Hotbar Mode
            if(e.getCurrentItem().getType().equals(Material.PAPER)) {
                instrument.playHotbar();
                return;
            }

            int octave = 1;
            if(e.getRawSlot() > 26) octave = 0;

            // Player clicked a natural note
            if(e.getCurrentItem().getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                String note = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                p.playNote(p.getLocation(), bukkitInstrument, Note.natural(octave, Note.Tone.valueOf(note)));
                return;
            }

            // Player clicked a sharp note
            if(e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                String note = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).charAt(0) + "";
                p.playNote(p.getLocation(), bukkitInstrument, Note.sharp(octave, Note.Tone.valueOf(note)));
                return;
            }
        }
    }
}
