package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.view.swing.panel.utils.CustomComponent;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel {
	private Controller controller;

	public WinPanel(Controller controller) {
		this.controller = controller;
		setBackground(Color.BLACK);

		JLabel title = new JLabel("You win!");

		JButton continuee = CustomComponent.createButton("Continue", controller::resume);
		JButton save = CustomComponent.createButton("Save", controller::saveMenu);

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
		c.insets = new Insets(0, 0, 0, 0);
		add(continuee, c);

		c.gridy = 2;
		add(save, c);
	}
}
