package io.github.zigeuh.swingy.controller;

import io.github.zigeuh.swingy.model.Hero;
import io.github.zigeuh.swingy.model.SaveManager;
import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.model.heroclass.AHeroClass;
import io.github.zigeuh.swingy.model.heroclass.HeroClassFactory;
import io.github.zigeuh.swingy.view.View;

import javax.swing.*;

public class Controller {

	private View view;
	private final SaveManager saveManager;

	private SaveSlot[] slots;

	public Controller() {
		this.saveManager = new SaveManager();
	}

	public void start() {
		SaveSlot[] saveSlots = loadSaves();

		SwingUtilities.invokeLater(() -> {
			this.view = new View(this);
			this.view.drawLoadMenu(saveSlots);
			this.view.setVisible(true);
		});
	}

	public void save(int slot, Hero hero) {
		System.out.println("Saving in slot " + slot);
		saveManager.save(slot, hero);
	}

	public SaveSlot[] loadSaves() {
		this.slots = saveManager.load();
		return this.slots;
	}

	public void chooseSlot(int slotId) {
		if (slotId <= 0 || slotId > 3) {
			throw new RuntimeException("Tried to load invalid slot");
		}

		SaveSlot slot = this.slots[slotId - 1];

		if (slot.isOccupied()) {
			System.out.println("Loading hero: " + slot.getHero().getHeroClass() + " " + slot.getHero().getName());
		}
		else {
			view.drawCharacterCreation();
		}
	}

	public void createCharacter(String name, String heroClassName) {
		System.out.println(("Creating the " + heroClassName + " " + name));

		String formattedName = heroClassName.replace(" ", "_").toUpperCase();

		System.out.println(formattedName);
		AHeroClass heroClass = HeroClassFactory.createClass(formattedName);

		save(1, new Hero(name, heroClass, 0, 0)); // TODO To remove since save will be done by the player in the game
	}
}
