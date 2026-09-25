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

	void showSave(SaveSlot[] slots);

	void showDelete(SaveSlot[] slots);

	void showWin();

	void showLoot(Artifact current, Artifact loot);

	void refreshGame();

}
