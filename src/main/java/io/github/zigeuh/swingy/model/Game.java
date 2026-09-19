package io.github.zigeuh.swingy.model;

public class Game {

	private Map map;
	private final Hero hero;

	public Game(Hero hero) {
		this.hero = hero;

		this.map = new Map(hero.getLevel());
	}

	public Map getMap() {
		return this.map;
	}

	public Hero getHero() {
		return this.hero;
	}

	public boolean move(Direction direction) {
		Coordinates currentPos = map.getHeroPos();

		int targetX = currentPos.x() + direction.getDx();
		int targetY = currentPos.y() + direction.getDy();

		map.setHeroPos(targetX, targetY);

		Enemy target = map.getGrid()[targetY][targetX];
		return target != null;
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
