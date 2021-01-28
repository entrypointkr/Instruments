package instruments.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

import instruments.Instrument;
import instruments.Instruments;
import instruments.Utils;

public class InventoryOpen implements Listener {

	private Instruments instance = Instruments.getInstance();

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		Player p = (Player) event.getPlayer();

		if (!instance.getInstrumentManager().containsKey(p))
			return;

		Instrument instrument = instance.getInstrumentManager().get(p);

		if (instrument.isHotBarMode()) {
			// Exit player from hot play mode

			Utils.loadInventory(p);

			for (int i = 0; i < 9; i++) {
				if (p.getInventory().getItem(i) == null)
					continue;
				if (p.getInventory().getItem(i).equals(instrument.getInstrumentType().getItemStack())) {
					p.getInventory().setHeldItemSlot(i);
					break;
				}
			}

			instrument.setHotBarMode(false);
			instrument.play();

			return;

		}
	}
}
