package instruments.listeners;

import instruments.Instrument;
import instruments.Instruments;
import instruments.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneak implements Listener {

    private Instruments instance = Instruments.getInstance();

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if(!instance.getInstrumentManager().containsKey(player)) return;

        Instrument instrument = instance.getInstrumentManager().get(player);
        if(instrument.isHotBarMode()) {
            Utils.loadInventory(player);
            instrument.setHotBarMode(false);
            instrument.play();
        }

    }

}
