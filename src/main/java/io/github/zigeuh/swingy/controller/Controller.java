package io.github.zigeuh.swingy.controller;

import io.github.zigeuh.swingy.model.*;
import io.github.zigeuh.swingy.model.artifact.Artifact;
import io.github.zigeuh.swingy.model.hero.Hero;
import io.github.zigeuh.swingy.model.hero.HeroClass;
import io.github.zigeuh.swingy.view.IView;
import io.github.zigeuh.swingy.view.ViewType;
import io.github.zigeuh.swingy.view.console.ConsoleView;
import io.github.zigeuh.swingy.view.swing.SwingView;
import jakarta.validation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Controller {

	private IView view;
	private ViewType currentView;
	private Game game;
	private BattleResult pendingResult;
	private SaveSlot[] slots;
	private int currentSlotId = -1;
	private final SaveManager saveManager;
	private final String persistenceError;
	private final ValidatorFactory factory;
	private final Validator validator;

	public Controller() {
		this.factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
		SaveManager manager = null;
		String error = null;
		try {
			manager = new SaveManager();
		} catch (Exception e) {
			error = e.getMessage() != null ? e.getMessage() : e.toString();
		}
		this.saveManager = manager;
		this.persistenceError = error;
		this.slots = emptySlots();
	}

	public boolean isPersistenceAvailable() {
		return this.saveManager != null;
	}

	public String getPersistenceError() {
		return this.persistenceError;
	}

	private static SaveSlot[] emptySlots() {
		return new SaveSlot[] {
			new SaveSlot(0, false, null),
			new SaveSlot(1, false, null),
			new SaveSlot(2, false, null)
		};
	}

	public void start() {
		SaveSlot[] saveSlots = loadSaves();
		view.showMenu(saveSlots);
	}

	public void saveGame() {
		try {
			saveSlot(currentSlotId);
		} catch (Exception e) {
			String message = e.getMessage() != null ? e.getMessage() : e.toString();
			view.showSaveFailed(message);
			return;
		}
		view.showSaveConfirmation();
	}

	public SaveSlot[] loadSaves() {
		if (saveManager == null) {
			this.slots = emptySlots();
			return this.slots;
		}
		try {
			this.slots = saveManager.load();
		} catch (Exception e) {
			this.slots = emptySlots();
		}
		return this.slots;
	}

	public IView getView() {
		return this.view;
	}

	public void chooseSlot(int slotId) {
		if (slotId < 0 || slotId >= 3 || slots == null || slots[slotId] == null) {
			if (view != null && slots != null) view.showMenu(slots);
			return;
		}

		SaveSlot slot = this.slots[slotId];

		this.currentSlotId = slotId;

		if (slot.isOccupied()) {
			startGame(new Hero(slot.getHero()));
		}
		else {
			view.showCreateHero(null, null);
		}
	}

	public void deleteSlot(int slotId) {
		if (slotId < 0 || slotId >= 3 || slots == null || slots[slotId] == null) {
			if (view != null && slots != null) view.showMenu(slots);
			return;
		}
		if (saveManager != null) {
			try {
				saveManager.delete(slotId);
			} catch (Exception ignored) {
			}
		}
		slots[slotId] = new SaveSlot(slotId, false, null);

		view.showMenu(slots);
	}

	private void saveSlot(int slotId) {
		if (slotId < 0 || slotId >= 3) {
			throw new RuntimeException("Tried to save invalid slot");
		}

		if (game == null) throw new RuntimeException("Tried to save while no game is started");

		if (saveManager == null) {
			throw new RuntimeException(persistenceError != null ? persistenceError : "Saving unavailable: database not initialized");
		}

		saveManager.save(slotId, game.getHero());
		slots[slotId] = new SaveSlot(slotId, true, new Hero(game.getHero()));
	}

	public void deleteMenu() {
		view.showDelete(slots);
	}

	public void createCharacter(String name, String heroClassName) {
		if (currentSlotId < 0 || currentSlotId >= 3 || slots == null) {
			if (view != null && slots != null) view.showMenu(slots);
			return;
		}

		HeroClass heroClass = null;
		if (heroClassName != null) {
			String formattedName = heroClassName.replace(" ", "_").toUpperCase();
			try {
				heroClass = HeroClass.valueOf(formattedName);
			} catch (IllegalArgumentException ignored) {}
		}

		Hero hero = new Hero(name, heroClass);

		Set<ConstraintViolation<Hero>> violationSet = validator.validate(hero);

		if (violationSet.isEmpty()) {
			if (saveManager != null) {
				try {
					saveManager.save(currentSlotId, hero);
				} catch (Exception e) {
					List<String> saveError = new ArrayList<>();
					saveError.add(e.getMessage() != null ? e.getMessage() : e.toString());
					view.showCreateHero(name, saveError);
					return;
				}
			}
			slots[currentSlotId] = new SaveSlot(currentSlotId, true, new Hero(hero));
			startGame(hero);
			return;
		}

		List<String> errors = new ArrayList<>();

		for (ConstraintViolation<Hero> violation: violationSet) {
			errors.add(violation.getMessage());
		}

		view.showCreateHero(name, errors);
	}

	public void pause() {
		view.showPause();
	}

	public void resume() {
		view.showGame(game);
	}

	public void backToMenu() {
		if (game != null && currentSlotId >= 0 && currentSlotId < 3) {
			try {
				saveSlot(currentSlotId);
			} catch (Exception ignored) {}
		}

		game = null;
		pendingResult = null;
		currentSlotId = -1;
		view.showMenu(slots);
	}

	public void quit() {
		if (game != null && currentSlotId >= 0 && currentSlotId < 3) {
			try {
				saveSlot(currentSlotId);
			} catch (Exception ignored) {
			}
		}
		factory.close();
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

			if (saveManager != null) {
				try {
					saveManager.delete(currentSlotId);
				} catch (Exception ignored) {
				}
			}
			slots[currentSlotId] = new SaveSlot(currentSlotId, false, null);
			currentSlotId = -1;

			Hero hero = game.getHero();
			game = null;

			view.showLose(hero.getName(), hero.getHeroClass().getDisplayName(), hero.getLevel());
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

	public void setView(ViewType viewType) {
		currentView = viewType;
		if (viewType == ViewType.CONSOLE) view = new ConsoleView(this);
		else view = new SwingView(this);
	}

	public void switchView() {
		view.closeView();

		if (currentView == ViewType.CONSOLE) setView(ViewType.GUI);
		else setView(ViewType.CONSOLE);

		view.showGame(game);
	}
}
