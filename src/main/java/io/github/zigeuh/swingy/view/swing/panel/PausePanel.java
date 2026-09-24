package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {
	private final Controller controller;

	public PausePanel(Controller controller) {
		this.controller = controller;

		JLabel title = new JLabel("Pause");

		JButton resume = new JButton("Resume");
		JButton save = new JButton("Save");
		JButton backToMenu = new JButton("Return to main-menu");

		title.setHorizontalAlignment(SwingConstants.CENTER);
		resume.setHorizontalAlignment(SwingConstants.CENTER);
		save.setHorizontalAlignment(SwingConstants.CENTER);
		backToMenu.setHorizontalAlignment(SwingConstants.CENTER);

		title.setFont(new Font("Arial", Font.PLAIN, 30));
		resume.setFont(new Font("Arial", Font.PLAIN, 20));
		save.setFont(new Font("Arial", Font.PLAIN, 20));
		backToMenu.setFont(new Font("Arial", Font.PLAIN, 20));

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
		add(resume, c);

		c.gridy = 2;
		c.insets = new Insets(0, 0, 0, 0);
		add(save, c);

		c.gridy = 3;
		add(backToMenu, c);

		resume.addActionListener(e -> controller.resume());

		save.addActionListener(e -> controller.saveMenu());

		backToMenu.addActionListener(e -> controller.backToMenu());
	}
}
