package io.github.zigeuh.swingy.model;

public class SaveManager {
	public static SaveSlot[] load() {

		SaveSlot[] saveSlots = new SaveSlot[3];
		for (int i = 0; i <= 2; i++) {
			saveSlots[i] = new SaveSlot(i + 1, false, null, 0);
		}

		return saveSlots;
	}
}
