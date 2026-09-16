package io.github.zigeuh.swingy.controller;

import io.github.zigeuh.swingy.model.*;
import io.github.zigeuh.swingy.model.heroclass.AHeroClass;
import io.github.zigeuh.swingy.model.heroclass.HeroClassFactory;
import io.github.zigeuh.swingy.view.View;

import javax.swing.*;

public class Controller {

	private View view;
	private Game game;
	private SaveSlot[] slots;
	private final SaveManager saveManager;

	public Controller() {
		this.saveManager = new SaveManager();
		this.view = new View(this);
	}

	public void start() {
		SaveSlot[] saveSlots = loadSaves();
		view.setVisible(true);
		view.showMenu(saveSlots);
	}

	public void save(int slot, Hero hero) {
		// TODO
		System.out.println("Saving in slot " + slot);
		saveManager.save(slot, hero);
	}

	public void saveMenu() {
		// TODO
	}

	public SaveSlot[] loadSaves() {
		this.slots = saveManager.load();
		return this.slots;
	}

	public View getView() {
		return this.view;
	}

	public void chooseSlot(int slotId) {
		if (slotId <= 0 || slotId > 3) {
			throw new RuntimeException("Tried to load invalid slot");
		}

		SaveSlot slot = this.slots[slotId - 1];

		if (slot.isOccupied()) {
			System.out.println("Loading hero: " + slot.getHero().getHeroClass() + " " + slot.getHero().getName());
			startGame(slot.getHero());
		}
		else {
			view.showCreateHero();
		}
	}

	public void createCharacter(String name, String heroClassName) {
		System.out.println(("Creating the " + heroClassName + " " + name));

		String formattedName = heroClassName.replace(" ", "_").toUpperCase();

		System.out.println(formattedName);
		AHeroClass heroClass = HeroClassFactory.createClass(formattedName);
		Hero hero = new Hero(name, heroClass, 0, 0);

		startGame(hero);
	}

	public void cancelCreation() {
		view.showMenu(slots);
	}

	public void pause() {
		view.showPause();
	}

	public void resume() {
		view.showGame(game);
	}

	public void backToMenu() {
		// TODO
	}

	public void move(Direction direction) {
		game.move(direction);
		view.refreshGame();
	}


	private void startGame(Hero hero) {
		this.game = new Game(hero);
		view.showGame(game);
	}
}
