package instruments.listeners;

import instruments.Instrument;
import instruments.InstrumentType;
import instruments.Instruments;
import org.bukkit.ChatColor;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    private Instruments instance = Instruments.getInstance();

    // Use instrument item
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if(event.getItem() == null)
            return;

        if(instance.getInstrumentManager().containsKey(p)) {
            Instrument instrument = instance.getInstrumentManager().get(p);

            if(!instrument.isHotBarMode()) return;

            if(!event.getItem().getType().equals(instrument.getInstrumentType().getMaterial())) return;

            org.bukkit.Instrument bukkitInstrument = org.bukkit.Instrument.valueOf(instrument.getInstrumentType().toString());

            int octave = 0;
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                octave = 1;
            }

            String text = ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName());
            String note = text.charAt(0) + "";
            boolean isSharp = text.length() > 1;

            Note newNote = Note.natural(octave, Note.Tone.valueOf(note));
            if(isSharp) newNote = Note.sharp(octave, Note.Tone.valueOf(note));

            p.playNote(p.getLocation(), bukkitInstrument, newNote);
            return;
        }

        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;

        InstrumentType instrumentType = null;
        for(InstrumentType type : InstrumentType.values()) {
            if(type.getMaterial().equals(event.getItem().getType())) {
                instrumentType = type;
                break;
            }
        }

        if(instrumentType == null) return;

        new Instrument(instrumentType, p).play();
    }

}
