package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.enemy.Enemy;
import io.github.zigeuh.swingy.model.enemy.EnemyFactory;

import java.util.Random;

public class Map {

	private static final Random RANDOM = new Random();

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

		double enemyChance = 0.05;

		int mid = Math.floorDiv(size, 2);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (x == mid && y == mid)
					newMap[y][x] = null;
				else
					newMap[y][x] = RANDOM.nextDouble() < enemyChance ? EnemyFactory.createEnemy(level) : null;
			}
		}

		return newMap;
	}

	public Enemy[][] getGrid() {
		Enemy[][] copy = new Enemy[grid.length][];
		for (int i = 0; i < grid.length; i++) copy[i] = grid[i].clone();
		return copy;
	}

	public Enemy getEnemyAt(Coordinates c) {
		return grid[c.y()][c.x()];
	}

	public void removeEnemyAt(Coordinates c) {
		grid[c.y()][c.x()] = null;
	}

	public void setHeroPos(Coordinates coordinates) {
		heroPos = coordinates;
	}

	public void setHeroPos(int x, int y) {
		heroPos = new Coordinates(x, y);
	}

	public Coordinates getHeroPos() {
		return heroPos;
	}
}
