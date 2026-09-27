package io.github.zigeuh.swingy.model.enemy;

import java.util.Random;

public class EnemyFactory {
	private EnemyFactory() {}

	private static final Random RANDOM = new Random();

	public static Enemy createEnemy(int heroLevel) {
		int level = Math.max(1, heroLevel + RANDOM.nextInt(-1, 2));

		return switch (RANDOM.nextInt(3)) {
			case 0 -> EnemyType.SHADOW.forLevel(level);
			case 1 -> EnemyType.RED_NOCTURNE.forLevel(level);
			default -> EnemyType.LARGE_BODY.forLevel(level);
		};
	}
}
