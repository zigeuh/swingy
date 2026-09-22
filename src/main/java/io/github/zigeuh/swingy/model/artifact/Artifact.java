package io.github.zigeuh.swingy.model.artifact;

public record Artifact(ArtifactTemplate template, int bonus) {

	public Artifact(ArtifactTemplate template, int bonus) {
		this.template = template;
		if (bonus < 0) throw new IllegalArgumentException("bonus < 0");
		this.bonus = bonus;
	}

	public Artifact(Artifact artifact) {
		this(artifact.template, artifact.bonus);
	}

	public ArtifactTemplate getTemplate() {
		return template;
	}

	public ArtifactType getType() {
		return template.getType();
	}

	public String getName() {
		return template.getName();
	}
}
