package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.heroclass.AHeroClass;

public class Hero {

	private final String name;
	private final AHeroClass heroClass;
	private int hp;
	private int level = 0;
	private int xp = 0;

	public Hero(String name, AHeroClass heroClass, int level, int xp) {
		this.name = name;
		this.heroClass = heroClass;
		this.hp = heroClass.getBaseHp();
		this.level = level;
		this.xp = xp;
	}

	public Hero(Hero hero) {
		this.name = hero.getName();
		this.heroClass = hero.getHeroClass();
		this.hp = heroClass.getBaseHp();
		this.level = hero.getLevel();
		this.xp = hero.getXp();
	}

	public void gainXp(int xp) {
		if (xp < 0) {
			throw new IllegalArgumentException("XP gain can't be negative");
		}

		this.xp += xp;

		int requiredXp = (int) (level * 1000 + Math.pow(level - 1, 2) * 450);

		while (requiredXp <= this.xp) {
			this.level++;
			this.hp = getMaxHp();
			this.xp -= requiredXp;
		}
	}

	public void setCurrentHp(int hp) {
		this.hp = hp;
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

	public int getMaxHp() {
		return heroClass.getBaseHp() + Math.floorDiv(Math.max(0, level), 2) * 5;
	}

	public int getAttack() {
		return heroClass.getBaseAttack() + Math.floorDiv(Math.max(0, level), 3) * 3;
	}

	public int getDefense() {
		return heroClass.getBaseDefense() + Math.floorDiv(Math.max(0, level), 3) * 2;
	}

	public int getHp() {
		return hp;
	}
}
