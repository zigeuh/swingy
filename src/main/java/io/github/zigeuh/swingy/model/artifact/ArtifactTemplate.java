package io.github.zigeuh.swingy.model.artifact;

import io.github.zigeuh.swingy.model.hero.HeroClass;

import java.util.Random;

public enum ArtifactTemplate {
	KINGDOM_KEY(ArtifactType.WEAPON, "Kingdom Key", HeroClass.KEYBLADE_MASTER, 2, 4, 1),
	HEROS_CREST(ArtifactType.WEAPON, "Hero's Crest", HeroClass.KEYBLADE_MASTER, 4, 6, 4),
	OATHKEEPER(ArtifactType.WEAPON, "Oathkeeper", HeroClass.KEYBLADE_MASTER, 6, 8, 8),
	OBLIVION(ArtifactType.WEAPON, "Oblivion", HeroClass.KEYBLADE_MASTER, 8, 10, 12),
	ULTIMA_WEAPON(ArtifactType.WEAPON, "Ultima Weapon", HeroClass.KEYBLADE_MASTER, 10, 14, 16),

	MAGES_STAFF(ArtifactType.WEAPON, "Mage's Staff", HeroClass.WIZARD, 2, 4, 1),
	COMET_STAFF(ArtifactType.WEAPON, "Comet Staff", HeroClass.WIZARD, 4, 6, 4),
	WISDOM_WAND(ArtifactType.WEAPON, "Wisdom Wand", HeroClass.WIZARD, 6, 8, 8),
	RISING_DRAGON(ArtifactType.WEAPON, "Rising Dragon", HeroClass.WIZARD, 8, 10, 12),
	SAVE_THE_QUEEN(ArtifactType.WEAPON, "Save The Queen", HeroClass.WIZARD, 10, 14, 16),

	KNIGHTS_SHIELD(ArtifactType.WEAPON, "Knight's Shield", HeroClass.DEFENDER, 2, 4, 1),
	ADAMANT_SHIELD(ArtifactType.WEAPON, "Adamant Shield", HeroClass.DEFENDER, 4, 6, 4),
	FALLING_STAR(ArtifactType.WEAPON, "Falling Star", HeroClass.DEFENDER, 6, 8, 8),
	OGRE_SHIELD(ArtifactType.WEAPON, "Ogre Shield", HeroClass.DEFENDER, 8, 10, 12),
	SAVE_THE_KING(ArtifactType.WEAPON, "Save The King", HeroClass.DEFENDER, 10, 14, 16),

	AEGIS_CHAIN(ArtifactType.ARMOR, "Aegis Chain", null, 1, 3, 1),
	POWER_BAND(ArtifactType.ARMOR, "Power Band", null, 3, 5, 4),
	BUSTER_BAND(ArtifactType.ARMOR, "Buster Band", null, 5, 7, 8),
	COSMIC_BELT(ArtifactType.ARMOR, "Cosmic Belt", null, 8, 10, 12),

	VENTUS_HELMET(ArtifactType.HELM, "Ventus Helmet", null, 4, 6, 1),
	TERRA_HELMET(ArtifactType.HELM, "Terra Helmet", null, 8, 12, 4),
	AQUA_HELMET(ArtifactType.HELM, "Aqua Helmet", null, 14, 18, 8),
	VANITAS_HELMET(ArtifactType.HELM, "Vanitas Helmet", null, 20, 24, 12);

	private final ArtifactType artifactType;
	private final String name;
	private final HeroClass heroClass;
	private final String sprite;
	private final int minBonus;
	private final int maxBonus;
	private final int requiredLevel;

	ArtifactTemplate(ArtifactType artifactType, String name, HeroClass heroClass, int minBonus, int maxBonus, int requiredLevel) {
		this.artifactType = artifactType;
		this.name = name;
		this.heroClass = heroClass;
		this.sprite = "/sprites/artifacts/"
				+ artifactType.name().toLowerCase() + "/"
				+ name.toLowerCase()
				.replace(" ", "_")
				.replace("'", "")
				+ ".png";
		this.minBonus = minBonus;
		this.maxBonus = maxBonus;
		this.requiredLevel = requiredLevel;
	}

	public Artifact rollStats(Random random) {
		return new Artifact(this, random.nextInt(minBonus, maxBonus + 1));
	}

	public HeroClass getHeroClass() {
		return heroClass;
	}

	public ArtifactType getType() {
		return artifactType;
	}

	public String getName() {
		return name;
	}

	public String getSprite() {
		return sprite;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}
}
