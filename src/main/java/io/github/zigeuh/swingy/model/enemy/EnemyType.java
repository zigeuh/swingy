package io.github.zigeuh.swingy.model.enemy;

public enum EnemyType {
	SHADOW("Shadow", "shadow.png", 1, 18, 9, 3) {
		@Override public Enemy forLevel(int level) {
			return new Enemy(this, level,
					getBaseHp() + level * 4,
					getBaseAttack() + level,
					getBaseDefense() + level / 2);
		}
	},
	LARGE_BODY("Large Body", "large_body.png", 3, 26, 10, 4) {
		@Override public Enemy forLevel(int level) {
			return new Enemy(this, level,
					getBaseHp() + level * 6,
					getBaseAttack() + level,
					getBaseDefense() + level / 3);
		}
	},
	RED_NOCTURNE("Red Nocturne", "red_nocturne.png", 1, 12, 11, 2) {
		@Override public Enemy forLevel(int level) {
			return new Enemy(this, level,
					getBaseHp() + level * 3,
					getBaseAttack() + level,
					getBaseDefense() + level / 5);
		}
	};

	private final String name;
	private final String sprite;
	private final int tilesHigh;
	private final int baseHp;
	private final int baseAttack;
	private final int baseDefense;

	EnemyType(String name, String sprite, int tilesHigh, int baseHp, int baseAttack,  int baseDefense) {
		this.name = name;
		this.sprite = "/sprites/enemies/" + sprite;
		this.tilesHigh = tilesHigh;
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

	public int getTilesHigh() {
		return tilesHigh;
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
