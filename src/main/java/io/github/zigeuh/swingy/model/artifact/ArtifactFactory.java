package io.github.zigeuh.swingy.model.artifact;

import io.github.zigeuh.swingy.model.enemy.Enemy;
import io.github.zigeuh.swingy.model.hero.Hero;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArtifactFactory {

	private ArtifactFactory() {}

	private static final Random RANDOM = new Random();

	public static Artifact rollDrop(Hero hero, Enemy enemy) {
		if (RANDOM.nextDouble() > 0.30) return null;

		List<ArtifactTemplate> pool = Arrays.stream(ArtifactTemplate.values())
				.filter(template -> template.getHeroClass() == null || template.getHeroClass() == hero.getHeroClass())
				.filter(template -> enemy.getLevel() >= template.getRequiredLevel())
				.toList();
		if (pool.isEmpty()) return null;

		return pool.get(RANDOM.nextInt(pool.size())).rollStats(RANDOM);
	}
}
