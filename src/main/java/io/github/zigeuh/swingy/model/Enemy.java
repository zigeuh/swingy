package io.github.zigeuh.swingy.model;

public class Enemy {
	private final String name;
	private int hp;
	private final int attack;
	private final int defense;

	public Enemy(String name, int hp, int attack, int defense) {
		this.name = name;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
	}

	public void takeDamage(int damage) {
		hp = Math.clamp(hp - damage , 0, hp);
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}
}
