package io.github.zigeuh.swingy.controller;

import io.github.zigeuh.swingy.model.Hero;
import io.github.zigeuh.swingy.model.SaveManager;
import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.view.View;

import javax.swing.*;

public class Controller {

	private View view;
	private final SaveManager saveManager;

	public Controller() {
		this.saveManager = new SaveManager();
	}

	public void start() {
		SaveSlot[] saveSlots = saveManager.load();

		SwingUtilities.invokeLater(() -> {
			this.view = new View(this);
			this.view.drawLoadMenu(saveSlots);
			this.view.setVisible(true);
		});
	}

	public void save(int slot, Hero hero) {
		saveManager.save(slot, hero);
	}

	public SaveSlot[] loadSaves() {
		return saveManager.load();
	}

	public void chooseSlot(int slot) {
		view.drawCharacterCreation();
	}

	public void createCharacter(String name, String heroClass) {
		System.out.println(("User wants to create the " + heroClass + " " + name));
	}
}
