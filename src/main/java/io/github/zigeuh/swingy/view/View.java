package io.github.zigeuh.swingy.view;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.Hero;
import io.github.zigeuh.swingy.model.SaveSlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View extends JFrame {

	private final Controller controller;
	private JComponent currentComponent;

	public View(Controller controller) {
		super("Kingdom Hearts: Tessellated Hearts");

		this.controller = controller;

		this.setSize(750, 750);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	public <T> void switchPanel(String name, T arg) {
		if (this.currentComponent != null)
			remove(currentComponent);

		System.out.println("Switching to " + name);
		switch (name) {
			case "menu" -> currentComponent = drawLoadMenu((SaveSlot[]) arg);

			case "create" -> {
				System.out.println("Create");
				currentComponent = drawCharacterCreation();
			}
		}

		add(currentComponent);
		revalidate();
		currentComponent.repaint();
	}

	private JPanel drawLoadMenu(SaveSlot[] saveSlots) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.BLACK);

		JLabel topLabel = new JLabel("Load", SwingConstants.LEFT);
		topLabel.setForeground(Color.WHITE);
		topLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 32));
		topLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

		panel.add(topLabel, BorderLayout.NORTH);

		JPanel main = new JPanel(new GridLayout(3, 1, 0, 10));
		main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		main.setBackground(Color.BLACK);

		for (SaveSlot save: saveSlots) {
			if (save.isOccupied()) {
				Hero hero = save.getHero();
				main.add(createSaveSlot(save.getSlotNumber(), hero.getName(), hero.getLevel(), hero.getXp()));
			}
			else {
				main.add(createEmptySlot(save.getSlotNumber()));
			}
		}

		panel.add(main, BorderLayout.CENTER);

		return panel;
	}

	private JPanel drawCharacterCreation() {

		JPanel dialog = new JPanel();

		JLabel title = new JLabel("Create your character");

		JLabel name = new JLabel("Name :");
		JTextField nameInput = new JTextField();

		JLabel heroClass = new JLabel("Class :");
		JComboBox<String> heroClassInput = new JComboBox<>();
		heroClassInput.addItem("Keyblade Master");
		heroClassInput.addItem("Wizard");
		heroClassInput.addItem("Defender");

		JButton button = new JButton("Create");
		JButton cancel = new JButton("Cancel");

		title.setHorizontalAlignment(SwingConstants.CENTER);
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		nameInput.setHorizontalAlignment(SwingConstants.CENTER);
		heroClass.setHorizontalAlignment(SwingConstants.RIGHT);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		cancel.setHorizontalAlignment(SwingConstants.CENTER);

		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		heroClassInput.setRenderer(renderer);

		title.setFont(new Font("Arial", Font.PLAIN, 30));
		name.setFont(new Font("Arial", Font.PLAIN, 20));
		nameInput.setFont(new Font("Arial", Font.PLAIN, 20));
		heroClass.setFont(new Font("Arial", Font.PLAIN, 20));
		heroClassInput.setFont(new Font("Arial", Font.PLAIN, 20));
		button.setFont(new Font("Arial", Font.PLAIN, 20));
		cancel.setFont(new Font("Arial", Font.PLAIN, 20));

		dialog.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(0, 0, 30, 0);
		dialog.add(title, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.ipady = 0;
		c.insets = new Insets(0, 0, 0, 0);
		dialog.add(name, c);

		c.gridx = 1;
		dialog.add(nameInput, c);

		c.gridx = 0;
		c.gridy = 2;
		dialog.add(heroClass, c);

		c.gridx = 1;
		dialog.add(heroClassInput, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.VERTICAL;
		c.ipady = 10;
		c.insets = new Insets(20, 0, 0, 0);
		dialog.add(button, c);

		c.gridy = 4;
		c.insets = new Insets(0, 0, 0, 0);
		dialog.add(cancel, c);

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String selected = (String) heroClassInput.getSelectedItem();
				controller.createCharacter(nameInput.getText(), selected);
			}
		});

		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.cancelCreation();
			}
		});

		return dialog;
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
				controller.chooseSlot(number);
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
