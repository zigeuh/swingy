package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.hero.Hero;

public class SaveSlot {

	private final int slotNumber;
	private final boolean occupied;
	private final Hero hero;

	public SaveSlot(int slotNumber, boolean occupied, Hero hero) {
		this.slotNumber = slotNumber;
		this.occupied = occupied;
		this.hero = hero;
	}

	public int getSlotNumber() {
		return this.slotNumber;
	}

	public boolean isOccupied() {
		return this.occupied;
	}

	public Hero getHero() {
		return this.hero;
	}
}
