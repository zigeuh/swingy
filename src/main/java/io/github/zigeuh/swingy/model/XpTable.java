package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.enemy.Enemy;

public final class XpTable {

	private XpTable() {}

	public static int requiredFor(int level) {
		if (level < 0) throw new IllegalArgumentException("level < 0");
		int prev = level - 1;
		return level * 1000 + prev * prev * 450;
	}

	public static int forVictory(Enemy enemy) {
		return enemy.getLevel() * 400 + enemy.getAttack() * 5 + enemy.getDefense() * 5;
	}
}
