package io.github.zigeuh.swingy.model.heroclass;

public abstract class AHeroClass {

	private final String name;
	private final int baseHp;
	private final int baseAttack;
	private final int baseDefense;

	protected AHeroClass(String name, int baseHp, int baseAttack, int baseDefense) {
		this.name = name;
		this.baseHp = baseHp;
		this.baseAttack = baseAttack;
		this.baseDefense = baseDefense;
	}

	public String getName() {
		return name;
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
}
