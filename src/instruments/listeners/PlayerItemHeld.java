package instruments.listeners;

import instruments.Instrument;
import instruments.Instruments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerItemHeld implements Listener {

    private Instruments instance = Instruments.getInstance();

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if(!instance.getInstrumentManager().containsKey(player)) return;

        Instrument instrument = instance.getInstrumentManager().get(player);

        if(!instrument.isHotBarMode() && !instrument.getScalesInventory().isQuickPlay()) return;

        ItemStack item = player.getInventory().getItem(event.getNewSlot());

        if(item == null || item.getItemMeta() == null) {
            event.setCancelled(true);
            return;
        }

        if(item.containsEnchantment(Enchantment.LURE)) {
            event.setCancelled(true);

            instrument.getScalesInventory().toggleOctave();
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + "Octave: " + instrument.getScalesInventory().getOctave());
            item.setItemMeta(itemMeta);
            return;
        }

        String note = ChatColor.stripColor(item.getItemMeta().getDisplayName());

        instrument.playNote(note, instrument.getScalesInventory().getOctave());

        event.setCancelled(true);
    }

}
