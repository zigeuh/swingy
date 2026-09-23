package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.artifact.ArtifactFactory;
import io.github.zigeuh.swingy.model.enemy.Enemy;
import io.github.zigeuh.swingy.model.hero.Hero;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {

	private Battle() {}

	private static int damage(int attackerAttack, int targetDefense) {
		Random random = new Random();
		int luck = random.nextInt(-2, 3);
		return Math.max(1, attackerAttack + luck - targetDefense);
	}

	public static BattleResult simulate(Hero hero, Enemy enemy) {
		int heroHp = hero.getHp();
		int enemyHp = enemy.getHp();

		List<RoundLog> rounds = new ArrayList<>();
		int round = 1;

		while (true) {
			int toEnemy = damage(hero.getAttack(), enemy.getDefense());
			enemyHp -= toEnemy;
			rounds.add(new RoundLog(round, hero.getName(), toEnemy, heroHp, enemyHp));
			if (enemyHp <= 0) {
				return new BattleResult(true, rounds, ArtifactFactory.rollDrop(hero, enemy), XpTable.forVictory(enemy), heroHp);
			}

			int toHero = damage(enemy.getAttack(), hero.getDefense());
			heroHp -= toHero;
			rounds.add(new RoundLog(round, enemy.getName(), toHero, heroHp, enemyHp));
			if (heroHp <= 0) {
				return new BattleResult(false, rounds, null, 0, 0);
			}

			round++;
			if (round > 100) throw new IllegalStateException("Battle lasting over 100 rounds");
		}
	}
}
