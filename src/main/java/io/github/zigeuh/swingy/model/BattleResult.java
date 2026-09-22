package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.artifact.Artifact;

import java.util.List;

public record BattleResult(boolean victory, List<RoundLog> rounds, Artifact loot, int xpGained, int heroHpLeft) {
	public BattleResult {
		rounds = List.copyOf(rounds);
	}
}
