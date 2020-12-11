package instruments;

import org.bukkit.entity.Player;

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
