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

	public Enemy move(Direction direction) {
		Coordinates currentPos = map.getHeroPos();

		int targetX = currentPos.x() + direction.getDx();
		int targetY = currentPos.y() + direction.getDy();

		map.setHeroPos(targetX, targetY);

		Enemy target = map.getGrid()[targetY][targetX];
		if (target != null) {
			return new Enemy("Test", 1, 1 ,1);
		}

		return null;
	}
}
