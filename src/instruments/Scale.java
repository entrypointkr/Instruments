package instruments;

import java.util.ArrayList;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.text.WordUtils;

public enum Scale {
	C_MAJOR, D_MAJOR, E_MAJOR, F_MAJOR, G_MAJOR, A_MAJOR, B_MAJOR, CSHARP_MAJOR, DSHARP_MAJOR, FSHARP_MAJOR,
	GSHARP_MAJOR, ASHARP_MAJOR;

	String[] notes = new String[] {"C", "C#", "D", "D#", "E", "F","F#", "G", "G#", "A", "A#", "B"}; 
	
	public String getName() {
		String name = this.toString();
		name = name.replaceAll("SHARP", "#");
		name = name.replaceAll("_", " ");
		name = WordUtils.capitalizeFully(name);
		return name;
	}
	
	public ArrayList<String> getNotes() {
		ArrayList<String> notes = new ArrayList<String>();
		String name = getName();
		int position = getIndexOfNote(getName().substring(0, name.indexOf(" ") + 1));
		int[] jumps = new int[7];
		
		switch (this.toString().substring(this.toString().indexOf("_") + 1)) {
		case "MAJOR":
			jumps = new int[] {0, 2, 2, 1, 2, 2, 2};
			break;
		}
		
		for (int jump : jumps) {
			position += jump;
			notes.add(this.notes[position]);
		}
		
		return notes;
	}
	
	public int getIndexOfNote(String note) {
		for (int i = 0; i < notes.length; i++) {
			if (notes[i].equals(note)) {
				return i;
			}
		}
		return -1;
	}
	
	public static Scale getScaleByName(String name) {
		for (Scale scale : Scale.values()) {
			if (scale.getName().equals(name))
				return scale;
		}
		
		return null;
	}
}
