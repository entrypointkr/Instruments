package instruments.listeners;

import instruments.Instruments;
import instruments.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {

    private Instruments instance = Instruments.getInstance();

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if(!instance.getInstrumentManager().containsKey(player)) return;

        Utils.loadInventory(player);
        instance.getInstrumentManager().remove(player);
    }
}
