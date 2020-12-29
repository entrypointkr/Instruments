package instruments.listeners;

import instruments.Instruments;
import instruments.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    private Instruments instance = Instruments.getInstance();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(!(event.getEntity() instanceof Player)) return;

        Player player = event.getEntity();
        if(!instance.getInstrumentManager().containsKey(player)) return;

        if(instance.getInstrumentManager().get(player).isHotBarMode()) {
            event.getDrops().clear();
        }
    }

}
