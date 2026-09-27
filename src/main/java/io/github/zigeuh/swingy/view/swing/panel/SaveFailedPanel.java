package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.view.swing.panel.utils.CustomComponent;

import javax.swing.*;
import java.awt.*;

public class SaveFailedPanel extends JPanel {
	private final Controller controller;
	private final JLabel title;

	public SaveFailedPanel(Controller controller) {
		this.controller = controller;
		setBackground(Color.BLACK);

		title = new JLabel("Game saving failed");

		JButton resume = CustomComponent.createButton("Resume", controller::resume);
		JButton retry = CustomComponent.createButton("Retry", controller::saveGame);

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
		add(retry, c);
	}

	public void setMessage(String message) {
		title.setText("Game saving failed: " + message);
	}
}
