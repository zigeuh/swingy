package io.github.zigeuh.swingy.view;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.Hero;
import io.github.zigeuh.swingy.model.SaveManager;
import io.github.zigeuh.swingy.model.SaveSlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View extends JFrame {

	public View() {
		super("Kingdom Hearts: Tessellated Hearts");

		this.setSize(750, 750);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		drawLoadMenu();
		this.getContentPane().setBackground(Color.BLACK);
	}

	private void drawLoadMenu() {
		JLabel topLabel = new JLabel("Load", SwingConstants.LEFT);
		topLabel.setForeground(Color.WHITE);
		topLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 32));
		topLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

		add(topLabel, BorderLayout.NORTH);

		JPanel main = new JPanel(new GridLayout(3, 1, 0, 10));
		main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		main.setBackground(Color.BLACK);

		SaveSlot[] saves = SaveManager.load();

		for (SaveSlot save: saves) {
			if (save.isOccupied()) {
				Hero hero = save.getHero();
				main.add(createSaveSlot(save.getSlotNumber(), hero.getName(), hero.getLevel(), hero.getXp()));
			}
			else {
				main.add(createEmptySlot(save.getSlotNumber()));
			}
		}

		add(main, BorderLayout.CENTER);
	}

	private JPanel createSaveBox(int number) {
		JPanel slot = new JPanel(new BorderLayout(10, 5));
		slot.setBackground(new Color(50, 50, 50));
		slot.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 1),
				BorderFactory.createEmptyBorder(10, 15, 10, 15)
		));

		slot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Controller.chooseSlot(number);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				slot.setBackground(new Color(41, 98, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				slot.setBackground(new Color(50, 50, 50));
			}
		});

		return slot;
	}

	private JPanel createSaveSlot(int index, String name, int level, int xp) {
		JPanel slot = createSaveBox(index);

		JLabel title = new JLabel("Slot " + index + " — " + name);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setForeground(Color.WHITE);
		slot.add(title, BorderLayout.NORTH);

		JPanel stats = new JPanel(new GridLayout(1, 3, 10, 0));
		stats.setOpaque(false);

		stats.add(createStatLabel("Level", String.valueOf(level)));
		stats.add(createStatLabel("XP", String.valueOf(xp)));

		slot.add(stats, BorderLayout.CENTER);

		return slot;
	}

	private JPanel createEmptySlot(int index) {
		JPanel slot = createSaveBox(index);

		JLabel title = new JLabel("Slot " + index + " - Empty", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setForeground(Color.GRAY);
		slot.add(title, BorderLayout.CENTER);

		return slot;
	}

	private JLabel createStatLabel(String key, String value) {
		JLabel label = new JLabel(
				"<html><center>" + key + "<br><b>" + value + "</b></center></html>",
				SwingConstants.CENTER
		);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		return label;
	}
}
