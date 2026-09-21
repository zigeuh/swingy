package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.enemy.Enemy;
import io.github.zigeuh.swingy.model.hero.Hero;

public class Game {

	private final Hero hero;
	private Map map;
	private Direction facing = Direction.SOUTH;

	public Game(Hero hero) {
		this.hero = hero;

		this.map = new Map(hero.getLevel());
	}

	public void healHero() {
		hero.setCurrentHp(hero.getMaxHp());
	}

	public void generateNewMap() {
		map = new Map(hero.getLevel());
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

		if (targetX >= map.getGrid().length || targetX < 0 || targetY >= map.getGrid().length || targetY < 0)
			return new MoveResult(true, null);

		map.setHeroPos(targetX, targetY);

		Enemy target = map.getGrid()[targetY][targetX];
		return new MoveResult(false, target);
	}

	public BattleResult fight() {
		BattleResult result = Battle.simulate(hero, map.getEnemyAt(map.getHeroPos()));
		hero.setCurrentHp(result.heroHpLeft());
		if (result.victory()) {
			map.removeEnemyAt(map.getHeroPos());
			hero.gainXp(result.xpGained());
		}
		return result;
	}
}
