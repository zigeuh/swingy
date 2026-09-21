package io.github.zigeuh.swingy.model.enemy;

public enum EnemyType {
	SHADOW("Shadow", "shadow.png", 15, 7, 3) {
		@Override public Enemy forLevel(int level) {
			return new Enemy(this, level,
					getBaseHp() + level * 6,
					getBaseAttack() + level,
					getBaseDefense() + level / 2);
		}
	},
	LARGE_BODY("Large Body", "large_body.png", 22, 9, 4) {
		@Override public Enemy forLevel(int level) {
			return new Enemy(this, level,
					getBaseHp() + level * 9,
					getBaseAttack() + level,
					getBaseDefense() + level / 3);
		}
	},
	RED_NOCTURNE("Red Nocturne", "red_nocturne.png", 12, 10, 2) {
		@Override public Enemy forLevel(int level) {
			return new Enemy(this, level,
					getBaseHp() + level * 4,
					getBaseAttack() + level * 2,
					getBaseDefense() + level / 5);
		}
	};

	private final String name;
	private final String sprite;
	private final int baseHp;
	private final int baseAttack;
	private final int baseDefense;

	EnemyType(String name, String sprite, int baseHp, int baseAttack,  int baseDefense) {
		this.name = name;
		this.sprite = "/sprites/enemies/" + sprite;
		this.baseHp = baseHp;
		this.baseAttack = baseAttack;
		this.baseDefense = baseDefense;
	}

	public String getName() {
		return name;
	}

	public String getSprite() {
		return sprite;
	}

	public int getBaseHp() {
		return baseHp;
	}

	public int getBaseAttack() {
		return baseAttack;
	}

	public int getBaseDefense() {
		return baseDefense;
	}

	public abstract Enemy forLevel(int level);
}
