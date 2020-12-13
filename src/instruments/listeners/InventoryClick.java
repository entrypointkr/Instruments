package instruments.listeners;

import instruments.Instrument;
import instruments.Instruments;
import instruments.Scale;
import instruments.Utils;
import inventories.ScalesInventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
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
            return;
        }

        if (e.getView().getTitle().contains("Instruments:")
                && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)
                && e.getView().getTopInventory().getType().equals(InventoryType.CHEST)) {
            e.setCancelled(true);

            String itemName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

            if(e.getCurrentItem().getType().equals(Material.PAPER)) {
                instrument.playScales();
                return;
            }

            if(itemName.contains("Chords")) {
                instrument.getInstrumentInventory().toggleChords();
                instrument.play();
                return;
            }

            org.bukkit.Instrument bukkitInstrument = org.bukkit.Instrument.valueOf(instrument.getInstrumentType().toString());
            boolean useChords = instrument.getInstrumentInventory().getChordsEnabled();

            int octave = 1;
            if(e.getRawSlot() > 26) octave = 0;

            // Player clicked a natural/sharp note
            if(e.getCurrentItem().getType().equals(Material.WHITE_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                String note = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).charAt(0) + "";

                if(useChords) {
                    if(e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                        boolean minor = e.getClick().equals(ClickType.SHIFT_LEFT) ? true : false;
                        String[] notes = minor ? Utils.getMinorTriad(note) : Utils.getMajorTriad(note);
                        for(int i = 0; i < notes.length; i++) {
                            if(notes[i].length() > 1) {
                                p.playNote(p.getLocation(), bukkitInstrument, Note.sharp(octave, Note.Tone.valueOf(notes[i].charAt(0) + "")));
                            } else {
                                p.playNote(p.getLocation(), bukkitInstrument, Note.natural(octave, Note.Tone.valueOf(notes[i])));
                            }

                        }
                        return;
                    }
                }

                boolean sharp = e.getCurrentItem().getType().equals(Material.WHITE_STAINED_GLASS_PANE) ? false : true;
                if(sharp) {
                    p.playNote(p.getLocation(), bukkitInstrument, Note.sharp(octave, Note.Tone.valueOf(note)));
                } else {
                    p.playNote(p.getLocation(), bukkitInstrument, Note.natural(octave, Note.Tone.valueOf(note)));
                }
                return;
            }

            return;
        }

        if (e.getView().getTitle().contains("Scales")
                && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)
                && e.getView().getTopInventory().getType().equals(InventoryType.CHEST)) {
            e.setCancelled(true);

            String itemName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

            if(itemName.contains(instrument.getInstrumentType().toString())) {
                instrument.play();
                return;
            }

            if(e.getCurrentItem().getType().equals(Material.PAPER)) {
                Scale selectedScale = Scale.getScaleByName(itemName);

                if(selectedScale == null) return;

                instrument.playHotbar(selectedScale);
                return;
            }

            if(e.getCurrentItem().getType().equals(Material.ARROW)) {
                int currPage = instrument.getScalesInventory().getPage();
                if(itemName.contains("Next")) {
                    instrument.getScalesInventory().setPage(currPage + 1);
                } else {
                    if(currPage != 0) {
                        instrument.getScalesInventory().setPage(currPage - 1);
                    }
                }
                instrument.playScales();
                return;
            }
        }
    }
}
