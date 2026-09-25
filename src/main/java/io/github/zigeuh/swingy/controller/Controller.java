package io.github.zigeuh.swingy.controller;

import io.github.zigeuh.swingy.model.*;
import io.github.zigeuh.swingy.model.artifact.Artifact;
import io.github.zigeuh.swingy.model.hero.Hero;
import io.github.zigeuh.swingy.model.hero.HeroClass;
import io.github.zigeuh.swingy.view.IView;
import jakarta.validation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Controller {

	private IView view;
	private Game game;
	private BattleResult pendingResult;
	private SaveSlot[] slots;
	private final SaveManager saveManager;
	private final Validator validator;

	public Controller() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
		this.saveManager = new SaveManager();
	}

	public void start() {
		SaveSlot[] saveSlots = loadSaves();
		view.showMenu(saveSlots);
	}

	public void saveMenu() {
		view.showSave(slots);
	}

	public SaveSlot[] loadSaves() {
		this.slots = saveManager.load();
		return this.slots;
	}

	public IView getView() {
		return this.view;
	}

	public void chooseSlot(int slotId) {
		if (slotId < 0 || slotId >= 3) {
			throw new RuntimeException("Tried to load invalid slot");
		}

		SaveSlot slot = this.slots[slotId];

		if (slot.isOccupied()) {
			startGame(new Hero(slot.getHero()));
		}
		else {
			view.showCreateHero(null);
		}
	}

	public void deleteSlot(int slotId) {
		if (slotId < 0 || slotId >= 3) {
			throw new RuntimeException("Tried to load invalid slot");
		}

		saveManager.delete(slotId);
		slots[slotId] = new SaveSlot(slotId, false, null);

		view.showMenu(slots);
	}

	public void saveSlot(int slotId) {
		if (slotId < 0 || slotId >= 3) {
			throw new RuntimeException("Tried to load invalid slot");
		}

		if (game == null) throw new RuntimeException("Tried to save while no game is started");

		saveManager.save(slotId, game.getHero());
		slots[slotId] = new SaveSlot(slotId, true, new Hero(game.getHero()));

		view.showGame(game);
	}

	public void deleteMenu() {
		view.showDelete(slots);
	}

	public void createCharacter(String name, String heroClassName) {

		HeroClass heroClass = null;
		if (heroClassName != null) {
			String formattedName = heroClassName.replace(" ", "_").toUpperCase();
			heroClass = HeroClass.valueOf(formattedName);
		}

		Hero hero = new Hero(name, heroClass);

		Set<ConstraintViolation<Hero>> violationSet = validator.validate(hero);

		if (violationSet.isEmpty()) {
			startGame(hero);
			return;
		}

		List<String> errors = new ArrayList<>();

		for (ConstraintViolation<Hero> violation: violationSet) {
			errors.add(violation.getMessage());
		}

		view.showCreateHero(errors);
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

	public void quit() {
		System.exit(0);
	}

	public void move(Direction direction) {
		MoveResult result = game.move(direction);
		if (result.win()) {
			game.win();
			view.showWin();
		}
		else if (result.enemy() != null) view.showStartBattle(result.enemy());
		else view.refreshGame();
	}

	public void fight() {
		this.pendingResult = game.fight();
		view.showBattleResult(this.pendingResult);
	}

	public void afterBattle() {
		if (pendingResult != null && pendingResult.victory()) {
			Artifact loot = pendingResult.loot();

			if (loot != null) view.showLoot(game.getHero().getArtifactOf(loot.getType()), loot);
			else {
				pendingResult = null;
				view.showGame(game);
			}
		} else {
			pendingResult = null;
			game = null;
			view.showMenu(slots);
		}
	}

	public void escape() {
		if (game.tryEscape()) resume();
		else fight();
	}

	private void startGame(Hero hero) {
		this.game = new Game(hero);
		view.showGame(game);
	}

	public void keepLoot() {
		game.getHero().equip(pendingResult.loot());
		pendingResult = null;
		view.showGame(game);
	}

	public void dropLoot() {
		pendingResult = null;
		view.showGame(game);
	}

	public void setView(IView view) {
		this.view = view;
	}
}
