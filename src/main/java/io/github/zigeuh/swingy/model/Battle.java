package io.github.zigeuh.swingy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {

	private Battle() {}

	private static int damage(int attackerAttack, int targetDefense) {
		Random random = new Random();
		int luck = random.nextInt(-2, 2);
		return Math.clamp(attackerAttack + luck - targetDefense, 0, attackerAttack);
	}

	public static BattleResult simulate(Hero hero, Enemy enemy) {
		int heroHp = hero.getHp();
		int enemyHp = enemy.getHp();

		List<RoundLog> rounds = new ArrayList<>();
		int round = 1;

		while (true) {
			int toEnemy = damage(hero.getAttack(), enemy.getDefense());
			enemyHp -= toEnemy;
			rounds.add(new RoundLog(round, enemy.getName(), toEnemy, heroHp, enemyHp));
			if (enemyHp <= 0) {
				return new BattleResult(true, rounds, 200, heroHp);
			}

			int toHero = damage(enemy.getAttack(), hero.getDefense());
			heroHp -= toHero;
			rounds.add(new RoundLog(round, hero.getName(), toHero, heroHp, enemyHp));
			if (heroHp <= 0) {
				return new BattleResult(false, rounds, 0, 0);
			}

			round++;
			if (round > 100) throw new IllegalStateException("Battle lasting over 100 rounds");
		}
	}
}
