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

	public void move(Direction direction) {
		Coordinates currentPos = map.getHeroPos();

		int targetX = currentPos.getX() + direction.getDx();
		int targetY = currentPos.getY() + direction.getDy();

		map.setHeroPos(targetX, targetY);
	}
}
