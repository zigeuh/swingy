package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.hero.Hero;

public class SaveSlot {

	private final int slotNumber;
	private final boolean occupied;
	private final Hero hero;
	private int playTimeSeconds;

	public SaveSlot(int slotNumber, boolean occupied, Hero hero, int playTimeSeconds) {
		this.slotNumber = slotNumber;
		this.occupied = occupied;
		this.hero = hero;
		this.playTimeSeconds = playTimeSeconds;
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
