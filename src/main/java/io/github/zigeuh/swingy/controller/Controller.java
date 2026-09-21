package io.github.zigeuh.swingy.controller;

import io.github.zigeuh.swingy.model.*;
import io.github.zigeuh.swingy.model.hero.Hero;
import io.github.zigeuh.swingy.model.hero.HeroClass;
import io.github.zigeuh.swingy.view.View;

import java.util.Random;

public class Controller {

	private final View view;
	private Game game;
	private BattleResult pendingResult;
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
		saveManager.save(slot, hero);
	}

	public void saveMenu() {
		view.showSave(slots);
	}

	public SaveSlot[] loadSaves() {
		this.slots = saveManager.load();
		return this.slots;
	}

	public View getView() {
		return this.view;
	}

	public void chooseSlot(int slotId) {
		if (slotId < 0 || slotId >= 3) {
			throw new RuntimeException("Tried to load invalid slot");
		}

		SaveSlot slot = this.slots[slotId];

		if (slot.isOccupied()) {
			System.out.println("Loading hero: " + slot.getHero().getHeroClass() + " " + slot.getHero().getName());
			startGame(new Hero(slot.getHero()));
		}
		else {
			view.showCreateHero();
		}
	}

	public void deleteSlot(int slotId) {
		if (slotId < 0 || slotId >= 3) {
			throw new RuntimeException("Tried to load invalid slot");
		}

		saveManager.delete(slotId);
		slots[slotId] = new SaveSlot(slotId, false, null, 0);

		view.showMenu(slots);
	}

	public void saveSlot(int slotId) {
		if (slotId < 0 || slotId >= 3) {
			throw new RuntimeException("Tried to load invalid slot");
		}

		if (game == null) throw new RuntimeException("Tried to save while no game is started");

		save(slotId, game.getHero());
		slots[slotId] = new SaveSlot(slotId, true, game.getHero(), 0);

		view.showGame(game);
	}

	public void deleteMenu() {
		view.showDelete(slots);
	}

	public void createCharacter(String name, String heroClassName) {
		System.out.println(("Creating the " + heroClassName + " " + name));

		String formattedName = heroClassName.replace(" ", "_").toUpperCase();

		System.out.println(formattedName);
		HeroClass heroClass = HeroClass.valueOf(formattedName);
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
		game = null;
		view.showMenu(slots);
	}

	public void move(Direction direction) {
		boolean enemyDetected = game.move(direction);
		if (enemyDetected) view.showStartBattle();
		else view.refreshGame();
	}

	public void fight() {
		this.pendingResult = game.fight();
		view.showBattleResult(this.pendingResult);
	}

	public void afterBattle() {
		if (pendingResult != null && pendingResult.victory()) {
			pendingResult = null;
			view.showGame(game);
		} else {
			pendingResult = null;
			game = null;
			view.showMenu(slots);
		}
	}

	public void escape() {
		Random rand = new Random();
		if (rand.nextDouble() < 0.5) fight();
		else resume();
	}

	private void startGame(Hero hero) {
		this.game = new Game(hero);
		view.showGame(game);
	}
}
