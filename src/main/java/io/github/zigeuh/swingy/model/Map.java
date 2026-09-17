package io.github.zigeuh.swingy.model;

import java.util.Random;

public class Map {

	private final Enemy[][] grid;
	private Coordinates heroPos;

	Map(int level) {
		this.grid = createNewMap(level);

		int mid = Math.floorDiv(grid.length, 2);
		this.heroPos = new Coordinates(mid, mid);
	}

	private Enemy[][] createNewMap(int level) {

		int size = (level - 1) * 5 + 10 - (level % 2);
		Enemy[][] newMap = new Enemy[size][size];

		Random rand = new Random();
		double enemyChance = 0.1;

		int mid = Math.floorDiv(size, 2);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (x == mid && y == mid)
					newMap[y][x] = null;
				else
					newMap[y][x] = rand.nextDouble() < enemyChance ? new Enemy("Test", 1, 1 ,1) : null;
			}
		}

		return newMap;
	}

	public Enemy[][] getGrid() {
		return grid;
	}

	public void setHeroPos(int x, int y) {
		heroPos = new Coordinates(x, y);
	}

	public Coordinates getHeroPos() {
		return heroPos;
	}
}
