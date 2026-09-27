package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.view.swing.panel.utils.CustomComponent;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {
	private final Controller controller;

	public PausePanel(Controller controller) {
		this.controller = controller;
		setBackground(Color.BLACK);

		JLabel title = new JLabel("Pause");

		JButton resume = CustomComponent.createButton("Resume", controller::resume);
		JButton save = CustomComponent.createButton("Save", controller::saveGame);
		JButton switchView = CustomComponent.createButton("Switch view", controller::switchView);
		JButton backToMenu = CustomComponent.createButton("Return to main-menu", controller::backToMenu);

		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
		title.setForeground(Color.WHITE);

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
		add(switchView, c);

		c.gridy = 4;
		add(backToMenu, c);
	}
}
