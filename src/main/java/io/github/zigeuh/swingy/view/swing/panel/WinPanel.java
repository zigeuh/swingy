package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel {
	private Controller controller;

	public WinPanel(Controller controller) {
		this.controller = controller;

		JLabel title = new JLabel("You win!");

		JButton save = new JButton("Save");

		title.setHorizontalAlignment(SwingConstants.CENTER);
		save.setHorizontalAlignment(SwingConstants.CENTER);

		title.setFont(new Font("Arial", Font.PLAIN, 30));
		save.setFont(new Font("Arial", Font.PLAIN, 20));

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
		add(save, c);

		save.addActionListener(e -> controller.saveMenu());
	}
}
