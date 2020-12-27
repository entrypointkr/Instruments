package instruments.listeners;

import instruments.Instrument;
import instruments.Instruments;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemHeld implements Listener {

    private Instruments instance = Instruments.getInstance();

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if(!instance.getInstrumentManager().containsKey(player)) return;

        Instrument instrument = instance.getInstrumentManager().get(player);

        if(!instrument.isHotBarMode() && !instrument.getScalesInventory().isQuickPlay()) return;

        ItemStack item = player.getInventory().getItem(event.getNewSlot());

        if(item == null || item.getItemMeta() == null) return;

        String note = ChatColor.stripColor(item.getItemMeta().getDisplayName());

        // TODO: Change octave Nick
        instrument.playNote(note, 0);

        player.getInventory().setHeldItemSlot(0);
    }

}
