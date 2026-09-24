package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.*;
import io.github.zigeuh.swingy.model.enemy.Enemy;
import io.github.zigeuh.swingy.model.enemy.EnemyType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.EnumMap;

public class GamePanel extends JPanel {
	private final Game game;
	private final Controller controller;
	private final MapCanvas mapCanvas;
	private final StatsPanel statsPanel;
	private final java.util.Map<Direction, Image> heroSprites = new EnumMap<>(Direction.class);
	private final java.util.Map<EnemyType, Image> enemySprites = new EnumMap<>(EnemyType.class);
	private String loadedFolder;

	public GamePanel(Game game, Controller controller) {
		this.game = game;
		this.controller = controller;

		setLayout(new BorderLayout());
		setBackground(Color.BLACK);

		mapCanvas = new MapCanvas();
		statsPanel = new StatsPanel(game.getMap());
		statsPanel.setHero(game.getHero());

		add(mapCanvas, BorderLayout.CENTER);
		add(statsPanel, BorderLayout.EAST);

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

	public void refresh() {
		statsPanel.setHero(game.getHero());
		mapCanvas.repaint();
	}

	private Action moveAction(Direction direction) {
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.move(direction);
			}
		};
	}

	private Image heroSprite(Direction facing, String folder) {
		if (!folder.equals(loadedFolder)) {
			heroSprites.clear();
			for (Direction dir : Direction.values()) {
				Image img = loadSprite(folder + "/" + dir.name().toLowerCase() + ".png");
				if (img != null) heroSprites.put(dir, img);
			}
			loadedFolder = folder;
		}
		return heroSprites.get(facing);
	}

	private static Image loadSprite(String path) {
		try (var stream = GamePanel.class.getResourceAsStream(path)) {
			if (stream == null) return null;
			return ImageIO.read(stream);
		} catch (IOException e) {
			return null;
		}
	}

	private Image enemySprite(EnemyType type) {
		if (!enemySprites.containsKey(type)) {
			enemySprites.put(type, loadSprite(type.getSprite()));
		}
		return enemySprites.get(type);
	}

	private class MapCanvas extends JPanel {
		MapCanvas() {
			setBackground(Color.BLACK);
		}

		private int tileSize() {
			return Math.clamp(Math.min(getWidth(), getHeight()) / 15, 16, 48);
		}

		private void drawSprite(Graphics g, Image sprite, int screenX, int screenY, int tile, int tilesHigh, Color invalid) {
			int realHigh = tilesHigh * tile;
			if (sprite == null) {
				g.setColor(invalid);
				g.fillRect(screenX, screenY, tile, tile);
			} else {
				int width = sprite.getWidth(null);
				int height = sprite.getHeight(null);
				if (width <= 0 || height <= 0) {
					g.drawImage(sprite, screenX, screenY + tile - realHigh, tile, realHigh, null);
				} else {
					int dw = Math.max(1, (int) Math.round((double) realHigh * width / height));
					int dx = screenX + (tile - dw) / 2;
					int dy = screenY + tile - realHigh;
					g.drawImage(sprite, dx, dy, dw, realHigh, null);
				}
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Map map = game.getMap();

			Enemy[][] grid = map.getGrid();
			if (grid == null || grid.length == 0 || grid[0].length == 0)
				return;

			int mapH = grid.length;
			int mapW = grid[0].length;

			int tile = tileSize();
			int padding = 12;
			int availW = getWidth() - padding * 2;
			int availH = getHeight() - padding * 2;
			if (availW <= 0 || availH <= 0)
				return;
			int visX = Math.min(availW / tile, mapW);
			int visY = Math.min(availH / tile, mapH);
			if (visX <= 0 || visY <= 0)
				return;

			Coordinates hero = map.getHeroPos();

			int camX = Math.clamp(hero.x() - visX / 2, 0, mapW - visX);
			int camY = Math.clamp(hero.y() - visY / 2, 0, mapH - visY);

			int offX = padding + (availW - visX * tile) / 2;
			int offY = padding + (availH - visY * tile) / 2;

			for (int y = 0; y < visY; y++) {
				for (int x = 0; x < visX; x++) {
					int screenX = offX + x * tile;
					int screenY = offY + y * tile;

					g.setColor(Color.GRAY);
					g.fillRect(screenX, screenY, tile, tile);
				}
			}

			for (int y = 0; y < visY; y++) {
				for (int x = 0; x < visX; x++) {
					int mapX = camX + x;
					int mapY = camY + y;
					int screenX = offX + x * tile;
					int screenY = offY + y * tile;

					if (hero.x() == mapX && hero.y() == mapY) {
						Image sprite = heroSprite(game.getFacing(), game.getHero().getHeroClass().getSpriteFolder());
						drawSprite(g, sprite, screenX, screenY, tile, game.getHero().getHeroClass().getTilesHigh(), Color.GREEN);

					} else {
						Enemy enemy = grid[mapY][mapX];

						if (enemy != null) {
							Image sprite = enemySprite(enemy.getType());
							drawSprite(g, sprite, screenX, screenY, tile, enemy.getType().getTilesHigh(), Color.RED);
						}
					}
				}
			}
		}
	}
}
