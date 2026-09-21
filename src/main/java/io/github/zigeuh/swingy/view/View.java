package io.github.zigeuh.swingy.view;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.BattleResult;
import io.github.zigeuh.swingy.model.Game;
import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.view.panel.*;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

	private final Controller controller;
	private final CardLayout cardLayout;

	private final MenuPanel menuPanel;
	private final CreateHeroPanel createHeroPanel;
	private final PausePanel pausePanel;
	private final StartBattlePanel startBattlePanel;
	private final BattleRecapPanel battleRecapPanel;
	private final SavePanel savePanel;
	private final DeletePanel deletePanel;
	private GamePanel gamePanel;

	public View(Controller controller) {
		super("Kingdom Hearts: Tessellated Hearts");

		this.controller = controller;
		this.cardLayout = new CardLayout();
		this.setLayout(this.cardLayout);

		menuPanel = new MenuPanel(controller);
		createHeroPanel = new CreateHeroPanel(controller);
		pausePanel = new PausePanel(controller);
		startBattlePanel = new StartBattlePanel(controller);
		battleRecapPanel = new BattleRecapPanel(controller);
		savePanel = new SavePanel(controller);
		deletePanel = new DeletePanel(controller);

		add("menu", menuPanel);
		add("create", createHeroPanel);
		add("pause", pausePanel);
		add("startBattle", startBattlePanel);
		add("battleRecap", battleRecapPanel);
		add("save", savePanel);
		add("delete", deletePanel);

		this.setSize(1050, 750);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	public void showMenu(SaveSlot[] slots) {
		menuPanel.setSlots(slots);
		cardLayout.show(getContentPane(), "menu");
	}

	public void showCreateHero() {
		cardLayout.show(getContentPane(), "create");
	}

	public void showPause() {
		cardLayout.show(getContentPane(), "pause");
	}

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

	public void showStartBattle() {
		cardLayout.show(getContentPane(), "startBattle");
	}

	public void showBattleResult(BattleResult result) {
		battleRecapPanel.setResult(result);
		cardLayout.show(getContentPane(), "battleRecap");
	}

	public void showSave(SaveSlot[] slots) {
		savePanel.setSlots(slots);
		cardLayout.show(getContentPane(), "save");
	}

	public void showDelete(SaveSlot[] slots) {
		deletePanel.setSlots(slots);
		cardLayout.show(getContentPane(), "delete");
	}

	public void refreshGame() {
		if (gamePanel != null) gamePanel.refresh();
	}
}
