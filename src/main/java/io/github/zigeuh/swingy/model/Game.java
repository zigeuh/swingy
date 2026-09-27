package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.enemy.Enemy;
import io.github.zigeuh.swingy.model.hero.Hero;

import java.util.Random;

public class Game {

	private final Hero hero;
	private Map map;
	private Coordinates previous;
	private Direction facing = Direction.SOUTH;
	private final Random random = new Random();

	public Game(Hero hero) {
		this.hero = hero;

		this.map = new Map(hero.getLevel());
	}

	public void healHero() {
		hero.setCurrentHp(hero.getMaxHp());
	}

	public void generateNewMap() {
		previous = null;
		map = new Map(hero.getLevel());
	}

	public void win() {
		generateNewMap();
		healHero();
	}

	public boolean tryEscape() {
		if (previous == null) return false;
		double rand =  random.nextDouble();

		if (rand >= 0.5) {
			map.setHeroPos(previous);
			return true;
		}

		return false;
	}

	public Map getMap() {
		return this.map;
	}

	public Hero getHero() {
		return this.hero;
	}

	public Direction getFacing() {
		return facing;
	}

	public MoveResult move(Direction direction) {
		this.facing = direction;
		Coordinates currentPos = map.getHeroPos();

		int targetX = currentPos.x() + direction.getDx();
		int targetY = currentPos.y() + direction.getDy();

		Enemy[][] grid = map.getGrid();
		if (targetX >= grid.length || targetX < 0 || targetY >= grid.length || targetY < 0)
			return new MoveResult(true, null);

		previous = currentPos;
		map.setHeroPos(targetX, targetY);

		Enemy target = grid[targetY][targetX];
		return new MoveResult(false, target);
	}

	public BattleResult fight() {
		Enemy enemy = map.getEnemyAt(map.getHeroPos());
		if (enemy == null) throw new IllegalStateException("No enemy on current position");
		BattleResult result = Battle.simulate(hero, enemy);
		hero.setCurrentHp(result.heroHpLeft());
		if (result.victory()) {
			map.removeEnemyAt(map.getHeroPos());
			hero.gainXp(result.xpGained());
		}
		return result;
	}
}
