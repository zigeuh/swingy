package io.github.zigeuh.swingy.model.hero;

public class Hero {

	private final String name;
	private final HeroClass heroClass;
	private int hp;
	private int level = 0;
	private int xp = 0;

	public Hero(String name, HeroClass heroClass, int level, int xp) {
		this(name, heroClass, level, xp, heroClass.getBaseHp());
	}

	public Hero(String name, HeroClass heroClass, int level, int xp, int currentHp) {
		this.name = name;
		this.heroClass = heroClass;
		if (level < 0) throw new IllegalArgumentException("level < 0");
		if (xp < 0) throw new IllegalArgumentException("xp < 0");
		this.level = level;
		this.xp = xp;
		this.hp = Math.clamp(currentHp, 0, getMaxHp());
	}

	public Hero(Hero hero) {
		this.name = hero.getName();
		this.heroClass = hero.getHeroClass();
		this.level = hero.getLevel();
		this.xp = hero.getXp();
		this.hp = Math.clamp(hero.getHp(), 0, getMaxHp());
	}

	public void gainXp(int xp) {
		if (xp < 0) {
			throw new IllegalArgumentException("XP gain can't be negative");
		}

		this.xp += xp;

		int requiredXp;

		while ((requiredXp = getRequiredXp()) <= this.xp) {
			this.level++;
			this.hp = getMaxHp();
			this.xp -= requiredXp;
		}
	}

	public void setCurrentHp(int hp) {
		this.hp = Math.clamp(hp, 0, getMaxHp());
	}

	public String getName() {
		return name;
	}

	public HeroClass getHeroClass() {
		return heroClass;
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		return xp;
	}

	public int getRequiredXp() {
		return (int) (level * 1000 + Math.pow(level - 1, 2) * 450);
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
