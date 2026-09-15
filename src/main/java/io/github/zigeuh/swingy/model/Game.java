package io.github.zigeuh.swingy.model;

public class Game {

	private Map map;
	private Hero hero;

	public Game(Hero hero) {
		this.hero = hero;

		this.map = new Map(hero.getLevel());
	}

	public Map getMap() {
		return this.map;
	}
}
