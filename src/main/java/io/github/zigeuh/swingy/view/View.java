package io.github.zigeuh.swingy.view;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.Game;
import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.view.panel.CreateHeroPanel;
import io.github.zigeuh.swingy.view.panel.GamePanel;
import io.github.zigeuh.swingy.view.panel.MenuPanel;
import io.github.zigeuh.swingy.view.panel.PausePanel;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

	private final Controller controller;
	private final CardLayout cardLayout;

	private final MenuPanel menuPanel;
	private final CreateHeroPanel createHeroPanel;
	private final PausePanel pausePanel;
	private GamePanel gamePanel;

	public View(Controller controller) {
		super("Kingdom Hearts: Tessellated Hearts");

		this.controller = controller;
		this.cardLayout = new CardLayout();
		this.setLayout(this.cardLayout);

		menuPanel = new MenuPanel(controller);
		createHeroPanel = new CreateHeroPanel(controller);
		pausePanel = new PausePanel(controller);

		add("menu", menuPanel);
		add("create", createHeroPanel);
		add("pause", pausePanel);

		this.setSize(750, 750);
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
		}
		cardLayout.show(getContentPane(), "game");
	}

	public void refreshGame() {
		if (gamePanel != null) gamePanel.repaint();
	}
}
