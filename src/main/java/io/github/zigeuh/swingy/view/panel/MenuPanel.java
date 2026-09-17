package io.github.zigeuh.swingy.view.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.Hero;
import io.github.zigeuh.swingy.model.SaveSlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {

	private final Controller controller;
	private final JPanel slotList = new JPanel(new GridLayout(3, 1, 0, 10));

	public MenuPanel(Controller controller) {
		this.controller = controller;
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		JPanel header = new JPanel(new BorderLayout());
		header.setOpaque(false);
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		JLabel title = new JLabel("Load", SwingConstants.LEFT);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 32));
		header.add(title, BorderLayout.WEST);

		JButton delete = new JButton("Delete");
		delete.setFont(new Font("Arial", Font.BOLD, 20));
		delete.setForeground(Color.WHITE);
		delete.setBackground(new Color(50, 50, 50));
		delete.setOpaque(true);
		delete.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 1),   // la bordure existante
				BorderFactory.createEmptyBorder(5, 10, 5, 10)    // padding: haut, gauche, bas, droite
		));
		delete.addActionListener(e -> controller.deleteMenu());
		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				delete.setBackground(new Color(41, 98, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				delete.setBackground(new Color(50, 50, 50));
			}
		});
		header.add(delete, BorderLayout.EAST);

		add(header, BorderLayout.NORTH);

		slotList.setOpaque(false);
		slotList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(slotList, BorderLayout.CENTER);
	}

	public void setSlots(SaveSlot[] slots) {
		slotList.removeAll();
		for (SaveSlot slot : slots) {
			slotList.add(slot.isOccupied()
					? createOccupiedSlot(slot)
					: createEmptySlot(slot.getSlotNumber()));
		}
		slotList.revalidate();
		slotList.repaint();
	}

	private JPanel createOccupiedSlot(SaveSlot slot) {
		Hero hero = slot.getHero();
		JPanel box = createSlotBox(slot.getSlotNumber());

		JLabel title = new JLabel("Slot " + (slot.getSlotNumber() + 1) + " — " + hero.getName());
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setForeground(Color.WHITE);
		box.add(title, BorderLayout.NORTH);

		JPanel stats = new JPanel(new GridLayout(1, 3, 10, 0));
		stats.setOpaque(false);

		stats.add(createStatLabel("Level", String.valueOf(hero.getLevel())));
		stats.add(createStatLabel("XP", String.valueOf(hero.getXp())));

		box.add(stats, BorderLayout.CENTER);

		return box;
	}

	private JPanel createEmptySlot(int number) {
		JPanel box = createSlotBox(number);

		JLabel title = new JLabel("Slot " + (number + 1) + " - Empty", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setForeground(Color.GRAY);
		box.add(title, BorderLayout.CENTER);

		return box;
	}

	private JPanel createSlotBox(int number) {
		JPanel box = new JPanel(new BorderLayout(10, 5));
		box.setBackground(new Color(50, 50, 50));
		box.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 1),
				BorderFactory.createEmptyBorder(10, 15, 10, 15)));

		box.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.chooseSlot(number);
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
