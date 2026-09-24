package io.github.zigeuh.swingy.view.swing;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.BattleResult;
import io.github.zigeuh.swingy.model.Game;
import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.model.artifact.Artifact;
import io.github.zigeuh.swingy.model.enemy.Enemy;
import io.github.zigeuh.swingy.view.IView;
import io.github.zigeuh.swingy.view.swing.panel.*;

import javax.swing.*;
import java.awt.*;

public class SwingView extends JFrame implements IView {

	private final Controller controller;
	private final CardLayout cardLayout;

	private final MenuPanel menuPanel;
	private final CreateHeroPanel createHeroPanel;
	private final PausePanel pausePanel;
	private final StartBattlePanel startBattlePanel;
	private final BattleRecapPanel battleRecapPanel;
	private final WinPanel winPanel;
	private final LootPanel lootPanel;
	private final SavePanel savePanel;
	private final DeletePanel deletePanel;
	private GamePanel gamePanel;

	public SwingView(Controller controller) {
		super("Kingdom Hearts: Tessellated Hearts");

		this.controller = controller;
		this.cardLayout = new CardLayout();
		this.setLayout(this.cardLayout);

		menuPanel = new MenuPanel(controller);
		createHeroPanel = new CreateHeroPanel(controller);
		pausePanel = new PausePanel(controller);
		startBattlePanel = new StartBattlePanel(controller);
		battleRecapPanel = new BattleRecapPanel(controller);
		winPanel = new WinPanel(controller);
		lootPanel = new LootPanel(controller);
		savePanel = new SavePanel(controller);
		deletePanel = new DeletePanel(controller);

		add("menu", menuPanel);
		add("create", createHeroPanel);
		add("pause", pausePanel);
		add("startBattle", startBattlePanel);
		add("battleRecap", battleRecapPanel);
		add("win", winPanel);
		add("loot", lootPanel);
		add("save", savePanel);
		add("delete", deletePanel);

		this.setSize(1050, 750);
		this.setMinimumSize(new Dimension(1050, 750));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void showMenu(SaveSlot[] slots) {
		menuPanel.setSlots(slots);
		cardLayout.show(getContentPane(), "menu");
	}

	@Override
	public void showCreateHero() {
		cardLayout.show(getContentPane(), "create");
	}

	@Override
	public void showPause() {
		cardLayout.show(getContentPane(), "pause");
	}

	@Override
	public void showGame(Game game) {
		if (gamePanel == null || gamePanel.getGame() != game) {
			if (gamePanel != null) remove(gamePanel);
			gamePanel = new GamePanel(game, controller);
			add("game", gamePanel);
		} else {
			gamePanel.refresh();
		}
		cardLayout.show(getContentPane(), "game");
	}

	@Override
	public void showStartBattle(Enemy enemy) {
		startBattlePanel.setEnemy(enemy);
		cardLayout.show(getContentPane(), "startBattle");
	}

	@Override
	public void showBattleResult(BattleResult result) {
		battleRecapPanel.setResult(result);
		cardLayout.show(getContentPane(), "battleRecap");
	}

	@Override
	public void showSave(SaveSlot[] slots) {
		savePanel.setSlots(slots);
		cardLayout.show(getContentPane(), "save");
	}

	@Override
	public void showDelete(SaveSlot[] slots) {
		deletePanel.setSlots(slots);
		cardLayout.show(getContentPane(), "delete");
	}

	@Override
	public void showWin() {
		cardLayout.show(getContentPane(), "win");
	}

	@Override
	public void showLoot(Artifact current, Artifact loot) {
		lootPanel.setLoot(current, loot);
		cardLayout.show(getContentPane(), "loot");
	}

	@Override
	public void refreshGame() {
		if (gamePanel != null) gamePanel.refresh();
	}
}
