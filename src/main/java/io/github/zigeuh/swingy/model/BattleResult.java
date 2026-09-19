package io.github.zigeuh.swingy.model;

import java.util.List;

public record BattleResult(boolean victory, List<RoundLog> rounds, int xpGained, int heroHpLeft) {
	public BattleResult {
		rounds = List.copyOf(rounds);
	}
}
