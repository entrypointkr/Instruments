package inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import instruments.InstrumentType;
import instruments.Scale;

public class ScalesInventory implements InventoryHolder {

	private Inventory inv;
	private InstrumentType instrumentType;

	public ScalesInventory(InstrumentType instrumentType) {
		inv = Bukkit.createInventory(this, 54, instrumentType.toString() + " Scales");
		this.instrumentType = instrumentType;
	}

	public void display(Player player, int page) {
		fillBackground();
		fillScales(page);

		inv.setItem(0, createInstrumentIcon());
		inv.setItem(45, createTile(Material.ARROW, "Previous Page"));
		inv.setItem(53, createTile(Material.ARROW, "Previous Page"));

		player.openInventory(this.inv);
	}

	private void fillBackground() {
		ItemStack backgroundTile = this.createTile(Material.BROWN_STAINED_GLASS_PANE, " ");
		for (int i = 0; i < 54; i++) {
			inv.setItem(i, backgroundTile);
		}
	}

	private void fillScales(int page) {
		int position = page * 16;
		Scale[] scales = Scale.values();
		for (int i = 10; i < 44 && position < scales.length; i += 2) {
			inv.setItem(i, createTile(Material.PAPER, scales[position].getName()));
			
			position++;
			if (i % 9 == 7)
				i++;
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

	@Override
	public Inventory getInventory() {
		return this.inv;
	}
}
