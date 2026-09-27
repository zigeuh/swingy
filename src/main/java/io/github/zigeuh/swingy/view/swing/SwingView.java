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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;

public class SwingView extends JFrame implements IView {

	private final Controller controller;
	private final CardLayout cardLayout;

	private final MenuPanel menuPanel;
	private final CreateHeroPanel createHeroPanel;
	private final PausePanel pausePanel;
	private final StartBattlePanel startBattlePanel;
	private final BattleRecapPanel battleRecapPanel;
	private final WinPanel winPanel;
	private final LosePanel losePanel;
	private final LootPanel lootPanel;
	private final SaveConfirmationPanel saveConfirmationPanel;
	private final SaveFailedPanel saveFailedPanel;
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
		losePanel = new LosePanel(controller);
		lootPanel = new LootPanel(controller);
		saveConfirmationPanel = new SaveConfirmationPanel(controller);
		saveFailedPanel = new SaveFailedPanel(controller);
		deletePanel = new DeletePanel(controller);

		add("menu", menuPanel);
		add("create", createHeroPanel);
		add("pause", pausePanel);
		add("startBattle", startBattlePanel);
		add("battleRecap", battleRecapPanel);
		add("win", winPanel);
		add("lose", losePanel);
		add("loot", lootPanel);
		add("saveConfirmation", saveConfirmationPanel);
		add("saveFailed", saveFailedPanel);
		add("delete", deletePanel);

		this.setSize(1050, 750);
		this.setMinimumSize(new Dimension(1050, 750));
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.quit();
			}
		});
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void showMenu(SaveSlot[] slots) {
		menuPanel.setSlots(slots);
		cardLayout.show(getContentPane(), "menu");
	}

	@Override
	public void showCreateHero(String name, List<String> errors) {
		createHeroPanel.setName(name);
		createHeroPanel.setErrors(errors);
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
	public void showSaveConfirmation() {
		cardLayout.show(getContentPane(), "saveConfirmation");
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
	public void showLose(String name, String heroClass, int level) {
		losePanel.setRecap(name, heroClass, level);
		cardLayout.show(getContentPane(), "lose");
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

	@Override
	public void showSaveFailed(String message) {
		saveFailedPanel.setMessage(message);
		cardLayout.show(getContentPane(), "saveFailed");
	}

	@Override
	public void closeView() {
		this.dispose();
	}
}
