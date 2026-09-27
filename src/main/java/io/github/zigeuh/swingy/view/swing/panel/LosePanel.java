package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.view.swing.panel.utils.CustomComponent;

import javax.swing.*;
import java.awt.*;

public class LosePanel extends JPanel {
	private final Controller controller;
	private final JLabel recap;

	public LosePanel(Controller controller) {
		this.controller = controller;
		setBackground(Color.BLACK);

		JLabel title = new JLabel("You lose...");

		recap = new JLabel();

		JButton menu = CustomComponent.createButton("Back to main-menu", controller::backToMenu);

		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
		title.setForeground(Color.WHITE);

		recap.setHorizontalAlignment(SwingConstants.CENTER);
		recap.setFont(new Font("Arial", Font.PLAIN, 20));
		recap.setForeground(Color.WHITE);

		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 30, 0);
		add(title, c);

		c.gridy = 1;
		c.insets = new Insets(0, 0, 5, 0);
		add(recap, c);

		c.gridy = 2;
		c.ipady = 10;
		c.insets = new Insets(0, 0, 0, 0);
		add(menu, c);
	}

	public void setRecap(String name, String heroClass, int level) {
		recap.setText(heroClass + " " + name + " died at level " + level);
	}
}
