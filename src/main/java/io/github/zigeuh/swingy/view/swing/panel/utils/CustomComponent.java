package io.github.zigeuh.swingy.view.swing.panel.utils;

import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.model.hero.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomComponent {
	public static JButton createButton(String text, Runnable action) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 20));
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(50, 50, 50));
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)
		));
		button.addActionListener(e -> action.run());
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(41, 98, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(new Color(50, 50, 50));
			}
		});
		return button;
	}

	public static JPanel createSlot(SaveSlot slot, Runnable action) {
		JPanel box = createSlotBox(action);

		if (slot.isOccupied()) {
			Hero hero = slot.getHero();

			JLabel title = new JLabel("Slot " + (slot.getSlotNumber() + 1) + " - " + hero.getName()
					+ " (" + hero.getHeroClass().getDisplayName() + ", Lv." + hero.getLevel() + ")");
			title.setFont(new Font("Arial", Font.BOLD, 18));
			title.setForeground(Color.WHITE);
			box.add(title, BorderLayout.NORTH);

			JPanel stats = new JPanel(new GridLayout(1, 2, 10, 0));
			stats.setOpaque(false);

			stats.add(createStatLabel("HP", hero.getHp() + "/" + hero.getMaxHp()));
			stats.add(createStatLabel("XP", hero.getXp() + "/" + hero.getRequiredXp()));

			box.add(stats, BorderLayout.CENTER);
		}
		else {
			JLabel title = new JLabel("Slot " + (slot.getSlotNumber() + 1) + " - Empty", SwingConstants.CENTER);
			title.setFont(new Font("Arial", Font.BOLD, 18));
			title.setForeground(Color.GRAY);
			box.add(title, BorderLayout.CENTER);
		}

		return box;
	}

	private static JPanel createSlotBox(Runnable action) {
		JPanel box = new JPanel(new BorderLayout(10, 5));
		box.setBackground(new Color(50, 50, 50));
		box.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 1),
				BorderFactory.createEmptyBorder(10, 15, 10, 15)));

		box.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				action.run();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				box.setBackground(new Color(41, 98, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				box.setBackground(new Color(50, 50, 50));
			}
		});
		return box;
	}

	private static JLabel createStatLabel(String key, String value) {
		JLabel label = new JLabel(
				"<html><center>" + key + "<br><b>" + value + "</b></center></html>",
				SwingConstants.CENTER
		);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		return label;
	}
}
