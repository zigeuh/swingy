package io.github.zigeuh.swingy.model.enemy;

import java.util.Random;

public class EnemyFactory {
	private EnemyFactory() {}

	public static Enemy createEnemy(int heroLevel) {
		Random random = new Random();
		int level = Math.max(1, heroLevel + random.nextInt(-1, 2));

		return switch (random.nextInt(2)) {
			case 0 -> EnemyType.SHADOW.forLevel(level);
			default -> EnemyType.BRUTE.forLevel(level);
		};
	}
}
