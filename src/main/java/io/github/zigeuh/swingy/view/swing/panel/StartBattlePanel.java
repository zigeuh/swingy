package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class StartBattlePanel extends JPanel {
	private final Controller controller;

	public StartBattlePanel(Controller controller) {
		this.controller = controller;

		JLabel title = new JLabel("An enemy is approaching...");

		JButton fight = new JButton("Fight");
		JButton escape = new JButton("Try to escape (50%)");

		title.setHorizontalAlignment(SwingConstants.CENTER);
		fight.setHorizontalAlignment(SwingConstants.CENTER);
		escape.setHorizontalAlignment(SwingConstants.CENTER);

		title.setFont(new Font("Arial", Font.PLAIN, 30));
		fight.setFont(new Font("Arial", Font.PLAIN, 20));
		escape.setFont(new Font("Arial", Font.PLAIN, 20));

		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 30, 0);
		add(title, c);

		c.gridy = 1;
		c.ipady = 10;
		c.insets = new Insets(20, 0, 0, 0);
		add(fight, c);

		c.gridy = 2;
		add(escape, c);

		fight.addActionListener(e -> controller.fight());
		escape.addActionListener(e -> controller.escape());
	}
}
