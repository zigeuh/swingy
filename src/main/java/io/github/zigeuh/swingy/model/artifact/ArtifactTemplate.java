package io.github.zigeuh.swingy.model.artifact;

import java.util.Random;

public enum ArtifactTemplate {
	KINGDOM_KEY(ArtifactType.WEAPON, "Kingdom Key", 2, 4, 1),
	HEROS_CREST(ArtifactType.WEAPON, "Hero's Crest", 4, 6, 5),
	OATHKEEPER(ArtifactType.WEAPON, "Oathkeeper", 6, 8, 10),
	OBLIVION(ArtifactType.WEAPON, "Oblivion", 8, 10, 15),
	ULTIMA_WEAPON(ArtifactType.WEAPON, "Ultima Weapon", 10, 14, 20),

	AEGIS_CHAIN(ArtifactType.ARMOR, "Aegis Chain", 1, 3, 1),
	POWER_BAND(ArtifactType.ARMOR, "Power Band", 3, 5, 5),
	BUSTER_BAND(ArtifactType.ARMOR, "Buster Band", 5, 7, 10),
	COSMIC_BELT(ArtifactType.ARMOR, "Cosmic Belt", 8, 10, 15),

	VENTUS_HELMET(ArtifactType.HELM, "Ventus Helmet", 4, 6, 1),
	TERRA_HELMET(ArtifactType.HELM, "Terra Helmet", 8, 12, 5),
	AQUA_HELMET(ArtifactType.HELM, "Aqua Helmet", 14, 18, 10),
	VANITAS_HELMET(ArtifactType.HELM, "Vanitas Helmet", 20, 24, 15);

	private final ArtifactType artifactType;
	private final String name;
	private final String sprite;
	private final int minBonus;
	private final int maxBonus;
	private final int requiredLevel;

	ArtifactTemplate(ArtifactType artifactType, String name, int minBonus, int maxBonus, int requiredLevel) {
		this.artifactType = artifactType;
		this.name = name;
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
