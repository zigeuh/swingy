package io.github.zigeuh.swingy.model.enemy;

import java.util.Random;

public class EnemyFactory {
	private EnemyFactory() {}

	public static Enemy createEnemy(int heroLevel) {
		Random random = new Random();
		int level = Math.max(1, heroLevel + random.nextInt(-1, 2));

		return switch (random.nextInt(3)) {
			case 0 -> EnemyType.SHADOW.forLevel(level);
			case 1 -> EnemyType.RED_NOCTURNE.forLevel(level);
			default -> EnemyType.LARGE_BODY.forLevel(level);
		};
	}
}
