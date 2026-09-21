package io.github.zigeuh.swingy.model.enemy;

public class Enemy {
	private final int hp;
	private final int attack;
	private final int defense;
	private final int level;
	private final EnemyType type;

	public Enemy(EnemyType type, int level, int hp, int attack, int defense) {
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.level = level;
		this.type = type;
	}

	public String getName() {
		return type.getName() + " Nv." + level;
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
