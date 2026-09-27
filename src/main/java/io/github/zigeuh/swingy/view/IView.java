package io.github.zigeuh.swingy.view;

import io.github.zigeuh.swingy.model.BattleResult;
import io.github.zigeuh.swingy.model.Game;
import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.model.artifact.Artifact;
import io.github.zigeuh.swingy.model.enemy.Enemy;

import java.util.List;

public interface IView {

	void showMenu(SaveSlot[] slots);

	void showCreateHero(String name, List<String> errors);

	void showPause();

	void showGame(Game game);

	void showStartBattle(Enemy enemy);

	void showBattleResult(BattleResult result);

	void showSaveConfirmation();

	void showDelete(SaveSlot[] slots);

	void showWin();

	void showLose(String name, String heroClass, int level);

	void showLoot(Artifact current, Artifact loot);

	void refreshGame();

	void showSaveFailed(String message);

	void closeView();
}
