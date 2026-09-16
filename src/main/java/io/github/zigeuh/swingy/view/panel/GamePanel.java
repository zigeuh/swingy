package io.github.zigeuh.swingy.view.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.Coordinates;
import io.github.zigeuh.swingy.model.Direction;
import io.github.zigeuh.swingy.model.Game;
import io.github.zigeuh.swingy.model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
	private final Game game;
	private final Controller controller;
	private static final int TILE_SIZE = 32;

	public GamePanel(Game game, Controller controller) {
		this.game = game;
		this.controller = controller;

		InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = getActionMap();

		inputMap.put(KeyStroke.getKeyStroke("LEFT"),   "moveWest");
		inputMap.put(KeyStroke.getKeyStroke("RIGHT"),  "moveEast");
		inputMap.put(KeyStroke.getKeyStroke("UP"),     "moveNorth");
		inputMap.put(KeyStroke.getKeyStroke("DOWN"),   "moveSouth");
		inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "pause");

		actionMap.put("moveWest",  moveAction(Direction.WEST));
		actionMap.put("moveEast",  moveAction(Direction.EAST));
		actionMap.put("moveNorth", moveAction(Direction.NORTH));
		actionMap.put("moveSouth", moveAction(Direction.SOUTH));
		actionMap.put("pause", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.pause();
			}
		});
	}

	public Game getGame() {
		return game;
	}

	private Action moveAction(Direction direction) {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.move(direction);
			}
		};
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Map map = this.game.getMap();

		boolean[][] grid = map.getGrid();
		if (grid == null || grid.length == 0 || grid[0].length == 0)
			return;

		int mapH = grid.length;
		int mapW = grid[0].length;

		int visX = Math.min(getWidth()  / TILE_SIZE, mapW);
		int visY = Math.min(getHeight() / TILE_SIZE, mapH);
		if (visX <= 0 || visY <= 0)
			return;

		Coordinates hero = map.getHeroPos();

		int camX = Math.clamp(hero.getX() - visX / 2, 0, mapW - visX);
		int camY = Math.clamp(hero.getY() - visY / 2, 0, mapH - visY);

		int offX = (getWidth()  - visX * TILE_SIZE) / 2;
		int offY = (getHeight() - visY * TILE_SIZE) / 2;

		for (int y = 0; y < visY; y++) {
			for (int x = 0; x < visX; x++) {
				int mapX = camX + x;
				int mapY = camY + y;
				int screenX = offX + x * TILE_SIZE;
				int screenY = offY + y * TILE_SIZE;

				if (hero.getX() == mapX && hero.getY() == mapY)
					g.setColor(Color.GREEN);
				else
					g.setColor(grid[mapY][mapX] ? Color.RED : Color.GRAY);

				g.fillRect(screenX, screenY, TILE_SIZE, TILE_SIZE);
			}
		}
	}
}
