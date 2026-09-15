package io.github.zigeuh.swingy.view;

import io.github.zigeuh.swingy.model.Coordinates;
import io.github.zigeuh.swingy.model.Map;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
	private Map map;

	public GameView(Map map) {
		this.map = map;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		boolean[][] grid = this.map.getGrid();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				int worldX = x * 100;
				int worldY = y * 100;

				Coordinates heroPos = this.map.getHeroPos();
				if (heroPos.getX() == x && heroPos.getY() == y)
					g.setColor(Color.GREEN);
				else
					g.setColor(grid[y][x] ? Color.RED : Color.GRAY);

				g.fillRect(worldX, worldY, 100, 100);
			}
		}
	}
}
