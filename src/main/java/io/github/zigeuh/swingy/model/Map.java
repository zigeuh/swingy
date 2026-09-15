package io.github.zigeuh.swingy.model;

import java.util.Random;

public class Map {

	private final boolean[][] grid;
	private Coordinates heroPos;

	Map(int level) {
		this.grid = createNewMap(level);

		int mid = Math.floorDiv(grid.length, 2);
		this.heroPos = new Coordinates(mid, mid);
	}

	private boolean[][] createNewMap(int level) {

		int size = (level - 1) * 5 + 10 - (level % 2);
		boolean[][] newMap = new boolean[size][size];

		Random rand = new Random();
		double enemyChance = 0.1;

		int mid = Math.floorDiv(size, 2);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (x == mid && y == mid)
					newMap[y][x] = false;
				else
					newMap[y][x] = rand.nextDouble() < enemyChance;
			}
		}

		return newMap;
	}

	public boolean[][] getGrid() {
		return grid;
	}

	public void setHeroPos(int x, int y) {
		heroPos = new Coordinates(x, y);
	}

	public Coordinates getHeroPos() {
		return heroPos;
	}
}
