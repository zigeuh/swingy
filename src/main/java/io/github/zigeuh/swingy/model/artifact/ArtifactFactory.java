package io.github.zigeuh.swingy.model.artifact;

import io.github.zigeuh.swingy.model.enemy.Enemy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArtifactFactory {

	private ArtifactFactory() {}

	public static Artifact rollDrop(Enemy enemy) {
		Random random = new Random();
		if (random.nextDouble() > 0.30) return null;

		List<ArtifactTemplate> pool = Arrays.stream(ArtifactTemplate.values())
				.filter(artifactTemplate -> enemy.getLevel() >= artifactTemplate.getRequiredLevel())
				.toList();
		if (pool.isEmpty()) return null;

		return pool.get(random.nextInt(pool.size())).rollStats(random);
	}
}
