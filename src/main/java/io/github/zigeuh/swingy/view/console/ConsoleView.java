package io.github.zigeuh.swingy.view.console;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.BattleResult;
import io.github.zigeuh.swingy.model.Coordinates;
import io.github.zigeuh.swingy.model.Direction;
import io.github.zigeuh.swingy.model.Game;
import io.github.zigeuh.swingy.model.Map;
import io.github.zigeuh.swingy.model.RoundLog;
import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.model.artifact.Artifact;
import io.github.zigeuh.swingy.model.artifact.ArtifactType;
import io.github.zigeuh.swingy.model.enemy.Enemy;
import io.github.zigeuh.swingy.model.hero.Hero;
import io.github.zigeuh.swingy.model.hero.HeroClass;
import io.github.zigeuh.swingy.view.IView;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleView implements IView {

	private static final String RESET = "\u001B[0m";
	private static final String BOLD = "\u001B[1m";
	private static final String RED = "\u001B[31m";
	private static final String GREEN = "\u001B[32m";
	private static final String YELLOW = "\u001B[33m";
	private static final String BLUE = "\u001B[34m";
	private static final String CYAN = "\u001B[36m";
	private static final String GRAY = "\u001B[90m";

	private static final int WIDTH = 46;

	private final Controller controller;
	private final Scanner in = new Scanner(System.in);
	private Game currentGame;
	private boolean inGame;
	private boolean creating;
	private List<String> pendingErrors;
	private String pendingName;

	private static final class Redraw extends RuntimeException {
		private Redraw() {}
	}

	private static final class CreateRedraw extends RuntimeException {
		private CreateRedraw() {}
	}

	public ConsoleView(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void showMenu(SaveSlot[] slots) {
		while (true) {
			clear();
			header("KINGDOM HEARTS: TESSELLATED HEARTS (Swingy)");
			if (!controller.isPersistenceAvailable()) {
				System.out.println("  " + YELLOW + "Saving unavailable, session will not be persisted." + RESET);
				if (controller.getPersistenceError() != null) {
					System.out.println("  " + GRAY + controller.getPersistenceError() + RESET);
				}
			}
			System.out.println("  Select your hero :");
			System.out.println();
			for (SaveSlot slot : slots) {
				System.out.println("  " + slotLine(slot));
			}
			System.out.println();
			System.out.println("  " + GRAY + "[1-3] play / create   [d]elete   [q]uit" + RESET);
			String choice = prompt("Choice").toLowerCase();
			switch (choice) {
				case "1", "2", "3" -> {
					controller.chooseSlot(Integer.parseInt(choice) - 1);
					return;
				}
				case "d" -> {
					controller.deleteMenu();
					return;
				}
				case "q" -> {
					System.out.println("  May your heart be your guiding key.");
					controller.quit();
					return;
				}
				default -> {
					System.out.println("  " + RED + "Invalid choice." + RESET);
					pressEnter();
				}
			}
		}
	}

	@Override
	public void showCreateHero(String nameHint, List<String> errors) {
		if (creating) {
			pendingErrors = errors;
			throw new CreateRedraw();
		}
		creating = true;
		try {
			String hint = nameHint;
			List<String> errs = errors;
			while (true) {
				try {
					createOnce(hint, errs);
					return;
				} catch (CreateRedraw r) {
					errs = pendingErrors;
					hint = pendingName;
				}
			}
		} finally {
			creating = false;
		}
	}

	private void createOnce(String nameHint, List<String> errors) {
		clear();
		header("CREATE YOUR HERO");

		if (errors != null && !errors.isEmpty()) {
			System.out.println("  " + RED + String.join("\n  ", errors) + RESET);
		}

		System.out.println();
		System.out.println("  Write a name :");
		System.out.println("  [c]ancel");
		String name;
		if (nameHint != null) {
			String input = prompt("Name [" + nameHint + "]");
			if (input.equals("c")) {
				controller.backToMenu();
				return;
			}
			name = input.isEmpty() ? nameHint : input;
		} else {
			name = prompt("Name");
			if (name.equals("c")) {
				controller.backToMenu();
				return;
			}
		}

		System.out.println();
		System.out.println("  Choose a class :");
		HeroClass[] classes = HeroClass.values();
		for (int i = 0; i < classes.length; i++) {
			HeroClass c = classes[i];
			System.out.println("  [" + (i + 1) + "] " + BOLD + c.getDisplayName() + RESET
					+ "  (HP " + c.getBaseHp() + " / ATK " + c.getBaseAttack() + " / DEF " + c.getBaseDefense() + ")");
		}
		System.out.println("  [c]ancel");
		String choice = prompt("Class").toLowerCase();
		if (choice.equals("c")) {
			controller.backToMenu();
			return;
		}

		pendingName = name;
		try {
			int index = Integer.parseInt(choice) - 1;
			if (index < 0 || index >= classes.length) throw new NumberFormatException();
			controller.createCharacter(name, classes[index].getDisplayName());
		} catch (NumberFormatException e) {
			controller.createCharacter(name, null);
		}
	}

	@Override
	public void showPause() {
		while (true) {
			clear();
			header("PAUSE");
			String choice = prompt("[r]esume  [s]ave  [m]enu").toLowerCase();
			switch (choice) {
				case "r" -> {
					controller.resume();
					return;
				}
				case "s" -> {
					controller.saveGame();
					return;
				}
				case "m" -> {
					controller.backToMenu();
					return;
				}
				default -> System.out.println("  " + RED + "Invalid choice." + RESET);
			}
		}
	}

	@Override
	public void showGame(Game game) {
		this.currentGame = game;
		if (inGame) throw new Redraw();
		inGame = true;
		try {
			while (true) {
				try {
					gameLoop(game);
					return;
				} catch (Redraw r) {
					game = currentGame;
				}
			}
		} finally {
			inGame = false;
		}
	}

	@Override
	public void refreshGame() {
		if (currentGame != null) throw new Redraw();
	}

	@Override
	public void showSaveFailed(String message) {
		while (true) {
			clear();
			header("GAME SAVED FAILED");
			System.out.println("  " + RED + message + RESET);

			System.out.println();
			System.out.println("  " + GRAY + "[c]ontinue   [r]etry" + RESET);
			String choice = prompt("Choice").toLowerCase();

			switch (choice) {
				case "c" -> {
					controller.resume();
					return;
				}
				case "r" -> {
					controller.saveGame();
					return;
				}
				default -> {
					System.out.println("  " + RED + "Invalid choice." + RESET);
					pressEnter();
				}
			}
		}
	}

	@Override
	public void showStartBattle(Enemy enemy) {
		while (true) {
			clear();
			header(RED + "An enemy is approaching..." + RESET);
			if (enemy != null) {
				System.out.println("  " + BOLD + enemy.getName() + RESET
						+ "  (HP " + enemy.getHp() + " / ATK " + enemy.getAttack() + " / DEF " + enemy.getDefense() + ")");
				System.out.println();
			}
			String choice = prompt("[f]ight  [r]un (50%)").toLowerCase();
			switch (choice) {
				case "f" -> {
					controller.fight();
					return;
				}
				case "r" -> {
					controller.escape();
					return;
				}
				default -> System.out.println("  " + RED + "Invalid choice." + RESET);
			}
		}
	}

	@Override
	public void showBattleResult(BattleResult result) {
		clear();
		if (result.victory()) header(GREEN + "Victory !" + RESET);
		else header(RED + "Defeat..." + RESET);
		for (RoundLog round : result.rounds()) {
			System.out.println("  " + BOLD + CYAN + "Round " + round.round() + RESET
					+ " - " + BOLD + round.attacker() + RESET
					+ " deals " + BOLD + YELLOW + round.damage() + " dmg" + RESET
					+ GRAY + "  (hero: " + round.heroHpLeft() + ", enemy: " + round.enemyHpLeft() + ")" + RESET);
		}
		System.out.println();
		System.out.println("  XP gained : " + GREEN + result.xpGained() + RESET);
		System.out.println("  Hero HP left : " + GREEN + result.heroHpLeft() + RESET);
		pressEnter();
		controller.afterBattle();
	}

	@Override
	public void showSaveConfirmation() {
		while (true) {
			clear();
			header("GAME SAVED SUCCESSFULLY");

			System.out.println();
			System.out.println("  " + GRAY + "[r]esume   [m]enu" + RESET);
			String choice = prompt("Choice").toLowerCase();

			switch (choice) {
				case "r" -> {
					controller.resume();
					return;
				}
				case "m" -> {
					controller.backToMenu();
					return;
				}
				default -> {
					System.out.println("  " + RED + "Invalid choice." + RESET);
					pressEnter();
				}
			}
		}
	}

	@Override
	public void showDelete(SaveSlot[] slots) {
		while (true) {
			clear();
			header("DELETE SAVE");
			for (SaveSlot slot : slots) {
				System.out.println("  " + slotLine(slot));
			}
			System.out.println();
			System.out.println("  " + GRAY + "[1-3] delete   [c]ancel" + RESET);
			String choice = prompt("Choice").toLowerCase();
			if (choice.equals("c")) {
				controller.backToMenu();
				return;
			}
			try {
				int index = Integer.parseInt(choice) - 1;
				if (index < 0 || index >= slots.length) throw new NumberFormatException();
				controller.deleteSlot(index);
				return;
			} catch (NumberFormatException e) {
				System.out.println("  " + RED + "Invalid slot." + RESET);
				pressEnter();
			}
		}
	}

	@Override
	public void showWin() {
		clear();
		header(GREEN + "YOU WIN !" + RESET);
		System.out.println("  The map is cleared. A new, bigger one awaits.");
		System.out.println();
		String choice = prompt("[Enter] continue  [s]ave").toLowerCase();
		if (choice.equals("s")) controller.saveGame();
		else controller.resume();
	}

	@Override
	public void showLose(String name, String heroClass, int level) {
		clear();
		header(RED + "YOU LOSE..." + RESET);
		System.out.println("  " + heroClass + " " + name + " died at level " + level);
		System.out.println();
		pressEnter();
		controller.backToMenu();
	}

	@Override
	public void showLoot(Artifact current, Artifact loot) {
		clear();
		header("LOOT !");
		System.out.println("  " + GRAY + "Equipped" + RESET + "  →  " + BOLD + "Dropped" + RESET);
		line();
		System.out.println("  " + lootLine("Old", current));
		System.out.println("  " + lootLine("New", loot));
		line();
		if (current == null) {
			System.out.println("  " + GREEN + "NEW item !" + RESET);
		} else {
			int diff = loot.bonus() - current.bonus();
			if (diff > 0) System.out.println("  " + GREEN + "+" + diff + " better" + RESET);
			else if (diff < 0) System.out.println("  " + RED + diff + " worse" + RESET);
			else System.out.println("  " + GRAY + "Equal bonus" + RESET);
		}
		System.out.println();
		String choice = prompt("[k]eep  [d]rop").toLowerCase();
		while (true) {
			switch (choice) {
				case "k" -> {
					controller.keepLoot();
					return;
				}
				case "d" -> {
					controller.dropLoot();
					return;
				}
				default -> {
					System.out.println("  " + RED + "Invalid choice." + RESET);
					choice = prompt("[k]eep  [d]rop").toLowerCase();
				}
			}
		}
	}

	private void gameLoop(Game game) {
		while (true) {
			clear();
			printHero(game.getHero());
			printMap(game);
			System.out.println();
			System.out.println("  " + GRAY + "[n]orth [s]outh [e]ast [w]est | [p]ause" + RESET);
			String choice = prompt("Move").toLowerCase();
			switch (choice) {
				case "n" -> {
					controller.move(Direction.NORTH);
					return;
				}
				case "s" -> {
					controller.move(Direction.SOUTH);
					return;
				}
				case "e" -> {
					controller.move(Direction.EAST);
					return;
				}
				case "w" -> {
					controller.move(Direction.WEST);
					return;
				}
				case "p" -> {
					controller.pause();
					return;
				}
				default -> System.out.println("  " + RED + "Invalid move." + RESET);
			}
		}
	}

	private void printHero(Hero hero) {
		header(hero.getName() + "  " + GRAY + hero.getHeroClass().getDisplayName()
				+ "  Lv." + hero.getLevel() + RESET);
		System.out.println("  HP  " + bar(hero.getHp(), hero.getMaxHp()) + " " + hero.getHp() + "/" + hero.getMaxHp());
		System.out.println("  XP  " + bar(hero.getXp(), hero.getRequiredXp()) + " " + hero.getXp() + "/" + hero.getRequiredXp());
		System.out.println("  ATK " + hero.getAttack() + "   DEF " + hero.getDefense());
		System.out.println("  " + GRAY + "Weapon:" + RESET + " " + artifactLine(hero.getWeapon())
				+ "   " + GRAY + "Armor:" + RESET + " " + artifactLine(hero.getArmor())
				+ "   " + GRAY + "Helm:" + RESET + " " + artifactLine(hero.getHelm()));
		System.out.println();
	}

	private void printMap(Game game) {
		Map map = game.getMap();
		Enemy[][] grid = map.getGrid();
		Coordinates hero = map.getHeroPos();
		int mapH = grid.length;
		int mapW = grid[0].length;
		int view = 15;
		int half = view / 2;
		int startX = Math.clamp(hero.x() - half, 0, Math.max(0, mapW - view));
		int startY = Math.clamp(hero.y() - half, 0, Math.max(0, mapH - view));
		int endX = Math.min(mapW, startX + view);
		int endY = Math.min(mapH, startY + view);
		System.out.println("  " + GRAY + "Map " + mapW + "x" + mapH
				+ "   You are at (" + hero.x() + ", " + hero.y() + ")"
				+ "   Reach any border to win" + RESET);
		for (int y = startY; y < endY; y++) {
			StringBuilder row = new StringBuilder("  ");
			for (int x = startX; x < endX; x++) {
				if (hero.x() == x && hero.y() == y) row.append(GREEN).append(BOLD).append('@').append(RESET);
				else if (grid[y][x] != null) row.append(RED).append('E').append(RESET);
				else row.append(GRAY).append('.').append(RESET);
			}
			System.out.println(row);
		}
		System.out.println("  " + GRAY + "@ you   E enemy" + RESET);
	}

	private String slotLine(SaveSlot slot) {
		String label = "[Slot " + (slot.getSlotNumber() + 1) + "]";
		if (!slot.isOccupied()) return label + " " + GRAY + "- Empty -" + RESET;
		Hero hero = slot.getHero();
		return label + " " + BOLD + hero.getName() + RESET
				+ " (" + hero.getHeroClass().getDisplayName()
				+ ", Lv." + hero.getLevel() + ", " + hero.getHp() + "/" + hero.getMaxHp() + " HP)";
	}

	private String artifactLine(Artifact artifact) {
		if (artifact == null) return GRAY + "Empty" + RESET;
		return artifact.getName() + " +" + artifact.bonus();
	}

	private String lootLine(String tag, Artifact artifact) {
		if (artifact == null) return tag + " : " + GRAY + "Empty slot" + RESET;
		return tag + " : " + BOLD + artifact.getName() + RESET
				+ " (" + statName(artifact.getType()) + " +" + artifact.bonus() + ")";
	}

	private String bar(int value, int max) {
		int width = 20;
		int filled = max <= 0 ? 0 : (int) Math.round((double) Math.clamp(value, 0, max) / max * width);
		return "[" + GREEN + "█".repeat(filled) + GRAY + "░".repeat(width - filled) + RESET + "]";
	}

	private void header(String title) {
		line();
		System.out.println("  " + BOLD + CYAN + title + RESET);
		line();
	}

	private void line() {
		System.out.println("  " + BLUE + "–".repeat(WIDTH) + RESET);
	}

	private void clear() {
		System.out.print("\u001B[H\u001B[2J");
		System.out.flush();
	}

	private String prompt(String label) {
		System.out.print("  " + YELLOW + label + " > " + RESET);
		try {
			return in.nextLine().trim();
		} catch (NoSuchElementException e) {
			controller.quit();
			throw new IllegalStateException("Unreachable: quit always exits", e);
		}
	}

	private void pressEnter() {
		System.out.print("  " + GRAY + "[Enter] continue" + RESET);
		try {
			in.nextLine();
		} catch (NoSuchElementException e) {
			controller.quit();
			throw new IllegalStateException("Unreachable: quit always exits", e);
		}
	}

	private static String statName(ArtifactType type) {
		return switch (type) {
			case WEAPON -> "Attack";
			case ARMOR -> "Defense";
			case HELM -> "HP";
		};
	}
}
