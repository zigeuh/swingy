package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.heroclass.AHeroClass;

public class Hero {

	private final String name;
	private final AHeroClass heroClass;
	private int level = 0;
	private int xp = 0;

	public Hero(String name, AHeroClass heroClass, int level, int xp) {
		this.name = name;
		this.heroClass = heroClass;
		this.level = level;
		this.xp = xp;
	}

	public String getName() {
		return name;
	}

	public AHeroClass getHeroClass() {
		return heroClass;
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		return xp;
	}

	public int getAttack() {
		return heroClass.getBaseAttack();
	}

	public int getDefense() {
		return heroClass.getBaseDefense();
	}

	public int getHp() {
		return heroClass.getBaseHp();
	}
}
