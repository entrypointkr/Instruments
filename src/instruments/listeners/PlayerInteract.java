package instruments.listeners;

import instruments.Instrument;
import instruments.InstrumentType;
import instruments.Instruments;
import org.bukkit.ChatColor;
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
