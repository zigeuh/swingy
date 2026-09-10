package io.github.zigeuh.swingy.model;

public class Hero {

	private String name;
	private String heroClass;
	private int level = 0;
	private int xp = 0;
	private int attack = 0;
	private int defense = 0;
	private int hp = 0;

	public Hero(String name, String heroClass, int level, int xp, int attack, int defense, int hp) {
		this.name = name;
		this.heroClass = heroClass;
		this.level = level;
		this.xp = xp;
		this.attack = attack;
		this.defense = defense;
		this.hp = hp;
	}

	public String getName() {
		return name;
	}

	public String getHeroClass() {
		return heroClass;
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		return xp;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getHp() {
		return hp;
	}
}
