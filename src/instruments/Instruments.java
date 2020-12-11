package instruments;

import instruments.commands.InstrumentsCommand;
import instruments.listeners.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;

public class Instruments extends JavaPlugin {

    private static Instruments instance;
    private HashMap<Player, Instrument> instrumentManager = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        getCommand("instruments").setExecutor(new InstrumentsCommand());
        this.registerListeners(
                new InventoryClick(),
                new InventoryClose(),
                new PlayerInteract(),
                new PlayerToggleSneak(),
                new PlayerDrop(),
                new PlayerPickup()
        );
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public HashMap<Player, Instrument> getInstrumentManager() {
        return instrumentManager;
    }

    public static Instruments getInstance() {
        return instance;
    }
}
