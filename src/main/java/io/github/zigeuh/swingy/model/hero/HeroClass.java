package io.github.zigeuh.swingy.model.hero;

public enum HeroClass {
	KEYBLADE_MASTER("Keyblade Master", "/sprites/sora", 32, 16, 6),
	WIZARD("Wizard", "/sprites/donald", 35, 11, 8),
	DEFENDER("Defender", "/sprites/goofy", 55, 7, 13);

	private final String displayName;
	private final String spriteFolder;
	private final int baseHp;
	private final int baseAttack;
	private final int baseDefense;

	HeroClass(String displayName, String spriteFolder, int baseHp, int baseAttack, int baseDefense) {
		this.displayName = displayName;
		this.spriteFolder = spriteFolder;
		this.baseHp = baseHp;
		this.baseAttack = baseAttack;
		this.baseDefense = baseDefense;
	}

	public String getDisplayName() {
		return displayName;
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

	public String getSpriteFolder() {
		return spriteFolder;
	}
}
