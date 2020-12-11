package instruments;


import org.bukkit.Material;

public enum InstrumentType {
    PIANO(Material.WOODEN_AXE),
    BASS_DRUM(Material.WOODEN_AXE),
    SNARE_DRUM(Material.WOODEN_AXE),
    STICKS(Material.WOODEN_AXE),
    BASS_GUITAR(Material.WOODEN_AXE),
    FLUTE(Material.WOODEN_AXE),
    BELL(Material.WOODEN_AXE),
    GUITAR(Material.WOODEN_AXE),
    CHIME(Material.WOODEN_AXE),
    XYLOPHONE(Material.WOODEN_AXE),
    IRON_XYLOPHONE(Material.WOODEN_AXE),
    COW_BELL(Material.WOODEN_AXE),
    DIDGERIDOO(Material.WOODEN_AXE),
    BIT(Material.WOODEN_AXE),
    BANJO(Material.WOODEN_AXE),
    PLING(Material.WOODEN_AXE);

    public final Material material;

    public Material getMaterial() {
        return this.material;
    }

    InstrumentType(Material material) {
        this.material = material;
    }
}
