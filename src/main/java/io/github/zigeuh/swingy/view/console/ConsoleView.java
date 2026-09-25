package io.github.zigeuh.swingy.view.console;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.BattleResult;
import io.github.zigeuh.swingy.model.Game;
import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.model.artifact.Artifact;
import io.github.zigeuh.swingy.model.enemy.Enemy;
import io.github.zigeuh.swingy.view.IView;

import java.util.List;

public class ConsoleView implements IView {
	public ConsoleView(Controller controller) {
	}

	@Override
	public void showMenu(SaveSlot[] slots) {

	}

	@Override
	public void showCreateHero(String name, List<String> errors) {

	}

	@Override
	public void showPause() {

	}

	@Override
	public void showGame(Game game) {

	}

	@Override
	public void showStartBattle(Enemy enemy) {

	}

	@Override
	public void showStartBattle() {

	}

	@Override
	public void showBattleResult(BattleResult result) {

	}

	@Override
	public void showSave(SaveSlot[] slots) {

	}

	@Override
	public void showDelete(SaveSlot[] slots) {

	}

	@Override
	public void showWin() {

	}

	@Override
	public void showLoot(Artifact current, Artifact loot) {

	}

	@Override
	public void refreshGame() {

	}
}
