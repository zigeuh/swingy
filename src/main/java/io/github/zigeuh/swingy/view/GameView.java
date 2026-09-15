package io.github.zigeuh.swingy.view;

import io.github.zigeuh.swingy.model.Coordinates;
import io.github.zigeuh.swingy.model.Map;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
	private Map map;
	private static final int TILE_SIZE = 32;

	public GameView(Map map) {
		this.map = map;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		boolean[][] grid = this.map.getGrid();
		if (grid == null || grid.length == 0 || grid[0].length == 0)
			return;

		int mapH = grid.length;
		int mapW = grid[0].length;

		int visX = Math.min(getWidth()  / TILE_SIZE, mapW);
		int visY = Math.min(getHeight() / TILE_SIZE, mapH);
		if (visX <= 0 || visY <= 0)
			return;

		Coordinates hero = this.map.getHeroPos();

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