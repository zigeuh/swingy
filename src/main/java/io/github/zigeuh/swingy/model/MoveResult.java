package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.enemy.Enemy;

public record MoveResult(boolean win, Enemy enemy) {}
