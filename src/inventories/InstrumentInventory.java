package inventories;

import instruments.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InstrumentInventory implements InventoryHolder {

	private Inventory inv;
	private InstrumentType instrumentType;
	private Instruments instance = Instruments.getInstance();

	public InstrumentInventory(InstrumentType instrumentType) {
		inv = Bukkit.createInventory(this, 54, "Instruments: " + Utils.formatString(instrumentType.toString()));
		this.instrumentType = instrumentType;
	}

	public void display(Player player) {
		if (!instance.getInstrumentManager().containsKey(player))
			return;

		instance.getInstrumentManager().get(player).setTransitioning(true);

		this.fillBackground();
		this.fillKeys();

		this.inv.setItem(0, this.createInstrumentIcon());
		this.inv.setItem(8, this.createScalesIcon());

		player.openInventory(this.inv);

		instance.getInstrumentManager().get(player).setTransitioning(false);
	}

	public void displayHotbar(Player player, Scale scale) {
		if (!instance.getInstrumentManager().containsKey(player))
			return;

		Instrument instrument = instance.getInstrumentManager().get(player);
		Inventory playerInv = player.getInventory();

		ArrayList<String> notes = scale.getNotes();
		ArrayList<String> missingNotes = scale.getMissingNotes();

		playerInv.setItem(0, this.instrumentType.getItemStack());

		ItemStack instrumentItem = this.instrumentType.getItemStack();
		instrumentItem.addUnsafeEnchantment(Enchantment.LURE, 1);
		ItemMeta itemMeta = instrumentItem.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN + "Octave: " + instrument.getScalesInventory().getOctave());
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		instrumentItem.setItemMeta(itemMeta);

		if (instrument.getScalesInventory().isQuickPlay())
			playerInv.setItem(8, instrumentItem);

		int offset = instrument.getScalesInventory().isQuickPlay() ? 1 : 0;
		for (int i = 0; i < notes.size(); i++) {
			playerInv.setItem(i + offset, this.createHotBarInstrument(notes.get(i)));
		}

		int i = 0;
		for (i = 0; i < missingNotes.size(); i++) {
			playerInv.setItem(i + 9, this.createHotBarInstrument(missingNotes.get(i)));
		}

		if (instrument.getScalesInventory().isQuickPlay()) {
			for (int x = i; x - i < Scale.notes.length; x++) {
				playerInv.setItem(x + 9, this.createDeepHotBarInstrument(Scale.notes[x - i]));
			}
		}

	}

	private void fillBackground() {
		ItemStack backgroundTile = this.createTile(Material.BROWN_STAINED_GLASS_PANE, " ");
		for (int i = 0; i < 54; i++) {
			inv.setItem(i, backgroundTile);
		}
	}

	private void fillKeys() {
		String[] naturalNotes = new String[] { "G", "A", "B", "C", "D", "E", "F" };

		String[] sharpNotes = new String[] { "F#", "G#", "A#", "C#", "D#" };

		// Sets white keys
		String whiteColor = ChatColor.AQUA + "";
		for (int i = 0; i < 2; i++) {
			if (i == 1)
				whiteColor = ChatColor.GREEN + "";
			for (int j = 0; j < 7; j++) {
				int base;
				if (i == 0) {
					base = 19;
				} else {
					base = 46;
				}

				inv.setItem(base + j, this.createTile(Material.WHITE_STAINED_GLASS_PANE, whiteColor + naturalNotes[j]));
			}
		}

		// Sets black keys
		String blackColor = ChatColor.DARK_AQUA + "";
		for (int i = 0; i < 2; i++) {
			if (i == 1)
				blackColor = ChatColor.DARK_GREEN + "";
			int keyIndex = 0;
			for (int j = 0; j < 6; j++) {
				int base;
				if (i == 0) {
					base = 10;
				} else {
					base = 37;
				}

				if (j != 3) {
					inv.setItem(base + j,
							this.createTile(Material.BLACK_STAINED_GLASS_PANE, blackColor + sharpNotes[keyIndex]));
					keyIndex++;
				}
			}
		}
	}

	private ItemStack createTile(Material material, String name) {
		ItemStack tile = new ItemStack(material, 1);
		ItemMeta itemMeta = tile.getItemMeta();
		itemMeta.setDisplayName(name);
		tile.setItemMeta(itemMeta);
		return tile;
	}

	private ItemStack createInstrumentIcon() {
		ItemStack icon = new ItemStack(Material.RED_WOOL, 1);
		ItemMeta itemMeta = icon.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RED + this.instrumentType.toString());
		icon.setItemMeta(itemMeta);
		return icon;
	}

	private ItemStack createScalesIcon() {
		ItemStack icon = new ItemStack(Material.PAPER, 1);
		ItemMeta itemMeta = icon.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RED + "Scales");
		icon.setItemMeta(itemMeta);
		return icon;
	}

	private ItemStack createHotBarInstrument(String name) {
		ItemStack hotBarInstrument = this.instrumentType.getItemStack();
		ItemMeta itemMeta = hotBarInstrument.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN + name);
		hotBarInstrument.setItemMeta(itemMeta);
		return hotBarInstrument;
	}

	private ItemStack createDeepHotBarInstrument(String name) {
		ItemStack hotBarInstrument = this.instrumentType.getItemStack();
		ItemMeta itemMeta = hotBarInstrument.getItemMeta();
		itemMeta.setDisplayName(ChatColor.DARK_GREEN + name);
		hotBarInstrument.setItemMeta(itemMeta);
		return hotBarInstrument;
	}

	@Override
	public Inventory getInventory() {
		return this.inv;
	}
}
