package io.github.zigeuh.swingy.model.hero;

import io.github.zigeuh.swingy.model.XpTable;
import io.github.zigeuh.swingy.model.artifact.Artifact;
import io.github.zigeuh.swingy.model.artifact.ArtifactType;

public class Hero {

	private final String name;
	private final HeroClass heroClass;
	private int hp;
	private int level = 0;
	private int xp = 0;
	private Artifact weapon;
	private Artifact armor;
	private Artifact helm;

	public Hero(String name, HeroClass heroClass) {
		this(name, heroClass, 0, 0, heroClass.getBaseHp(), null, null, null);
	}

	public Hero(String name, HeroClass heroClass, int level, int xp, int currentHp, Artifact weapon, Artifact armor, Artifact helm) {
		this.name = name;
		this.heroClass = heroClass;
		if (level < 0) throw new IllegalArgumentException("level < 0");
		if (xp < 0) throw new IllegalArgumentException("xp < 0");
		this.level = level;
		this.xp = xp;
		this.hp = Math.clamp(currentHp, 0, getMaxHp());
		this.weapon = weapon;
		this.armor = armor;
		this.helm = helm;
	}

	public Hero(Hero hero) {
		this.name = hero.getName();
		this.heroClass = hero.getHeroClass();
		this.level = hero.getLevel();
		this.xp = hero.getXp();
		this.weapon = hero.getWeapon() == null ? null : new Artifact(hero.getWeapon());
		this.armor = hero.getArmor() == null ? null : new Artifact(hero.getArmor());
		this.helm = hero.getHelm() == null ? null : new Artifact(hero.getHelm());
		this.hp = Math.clamp(hero.getHp(), 0, getMaxHp());
	}

	public void gainXp(int xp) {
		if (xp < 0) {
			throw new IllegalArgumentException("XP gain can't be negative");
		}

		if (level >= 20) {
			return;
		}

		this.xp += xp;

		int requiredXp;

		while ((requiredXp = getRequiredXp()) <= this.xp) {
			this.level++;
			if (level >= 20) this.xp = 0;
			else this.xp -= requiredXp;
			this.hp = getMaxHp();
		}
	}

	public void setCurrentHp(int hp) {
		this.hp = Math.clamp(hp, 0, getMaxHp());
	}

	public void equip(Artifact artifact) {
		switch (artifact.getType()) {
			case WEAPON -> weapon = artifact;
			case ARMOR -> armor = artifact;
			case HELM -> {
				helm = artifact;
				hp = Math.clamp(hp, 0, getMaxHp());
			}
		}
	}

	public Artifact getWeapon() {
		return weapon;
	}

	public Artifact getArmor() {
		return armor;
	}

	public Artifact getHelm() {
		return helm;
	}

	public Artifact getArtifactOf(ArtifactType type) {
		switch (type) {
			case HELM -> {
				return helm;
			}
			case ARMOR -> {
				return armor;
			}
			case WEAPON -> {
				return weapon;
			}
		}
		return null;
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
		return XpTable.requiredFor(level + 1);
	}

	public int getMaxHp() {
		return heroClass.getBaseHp() + Math.floorDiv(Math.max(0, level), 2) * 5 + (helm != null ? helm.bonus() : 0);
	}

	public int getAttack() {
		return heroClass.getBaseAttack() + Math.floorDiv(Math.max(0, level), 3) * 3 + (weapon != null ? weapon.bonus() : 0);
	}

	public int getDefense() {
		return heroClass.getBaseDefense() + Math.floorDiv(Math.max(0, level), 3) * 2 + (armor != null ? armor.bonus() : 0);
	}

	public int getHp() {
		return hp;
	}
}
