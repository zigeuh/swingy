package io.github.zigeuh.swingy.controller;

import io.github.zigeuh.swingy.model.*;
import io.github.zigeuh.swingy.model.heroclass.AHeroClass;
import io.github.zigeuh.swingy.model.heroclass.HeroClassFactory;
import io.github.zigeuh.swingy.view.View;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {

	private View view;
	private final SaveManager saveManager;
	private Game game;

	private SaveSlot[] slots;

	public Controller() {
		this.saveManager = new SaveManager();
	}

	public void start() {
		SaveSlot[] saveSlots = loadSaves();

		SwingUtilities.invokeLater(() -> {
			this.view = new View(this);
			this.view.switchPanel("menu", saveSlots);
			this.view.addKeyListener(this);
			this.view.setVisible(true);
		});
	}

	public void save(int slot, Hero hero) {
		System.out.println("Saving in slot " + slot);
		saveManager.save(slot, hero);
	}

	public SaveSlot[] loadSaves() {
		this.slots = saveManager.load();
		return this.slots;
	}

	public void chooseSlot(int slotId) {
		if (slotId <= 0 || slotId > 3) {
			throw new RuntimeException("Tried to load invalid slot");
		}

		SaveSlot slot = this.slots[slotId - 1];

		if (slot.isOccupied()) {
			System.out.println("Loading hero: " + slot.getHero().getHeroClass() + " " + slot.getHero().getName());
			startGame(slot.getHero());
		}
		else {
			view.switchPanel("create", null);
		}
	}

	public void createCharacter(String name, String heroClassName) {
		System.out.println(("Creating the " + heroClassName + " " + name));

		String formattedName = heroClassName.replace(" ", "_").toUpperCase();

		System.out.println(formattedName);
		AHeroClass heroClass = HeroClassFactory.createClass(formattedName);
		Hero hero = new Hero(name, heroClass, 0, 0);

		startGame(hero);

		//save(1, new Hero(name, heroClass, 0, 0)); // TODO To remove since save will be done by the player in the game
	}

	public void cancelCreation() {
		view.switchPanel("menu", slots);
	}

	private void startGame(Hero hero) {
		this.game = new Game(hero);

		view.switchPanel("game", game.getMap());
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	public void move(int x, int y) {
		Map map = game.getMap();
		Coordinates pos = map.getHeroPos();

		map.setHeroPos(pos.getX() + x, pos.getY() + y);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT -> move(-1, 0);
			case KeyEvent.VK_UP -> move(0, -1);
			case KeyEvent.VK_RIGHT -> move(1, 0);
			case KeyEvent.VK_DOWN -> move(0, 1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
