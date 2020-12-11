package instruments;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import inventories.InstrumentInventory;

public class Instrument {

    private InstrumentType instrumentType;
    private Player player;
    private InstrumentInventory instrumentInventory;
    private Instruments instance = Instruments.getInstance();
    private boolean hotBarMode;

    public Instrument(InstrumentType instrumentType, Player player) {
        this.instrumentType = instrumentType;
        this.player = player;
        this.instrumentInventory = new InstrumentInventory(instrumentType);

        this.instance.getInstrumentManager().put(player, this);
    }

    public void play() {
        this.instrumentInventory.display(this.player);
    }

    public void playHotbar() {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,  TextComponent.fromLegacyText(ChatColor.RED + "Press SHIFT to exit hotkey mode."));

        this.hotBarMode = true;

        Utils.storeInventory(this.player);

        player.closeInventory();

        // Clear everything but armor
        Utils.clearInventory(player);
        this.instrumentInventory.displayHotbar(this.player);
    }

    public InstrumentType getInstrumentType() {
        return this.instrumentType;
    }

    public boolean isHotBarMode() {
        return hotBarMode;
    }

    public void setHotBarMode(boolean hotBarMode) {
        this.hotBarMode = hotBarMode;
    }
}
