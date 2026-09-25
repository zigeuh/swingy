package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.view.swing.panel.utils.CustomComponent;

import javax.swing.*;
import java.awt.*;

public class CreateHeroPanel extends JPanel {
	private final Controller controller;
	private final JTextField nameInput = new JTextField();
	private final JLabel nameError = new JLabel("", SwingConstants.CENTER);

	public CreateHeroPanel(Controller controller) {
		this.controller = controller;
		setBackground(Color.BLACK);

		JLabel title = new JLabel("Create your character");

		JLabel name = new JLabel("Name :");

		JLabel heroClass = new JLabel("Class :");
		JComboBox<String> heroClassInput = new JComboBox<>();
		heroClassInput.addItem("Keyblade Master");
		heroClassInput.addItem("Wizard");
		heroClassInput.addItem("Defender");

		JButton create = CustomComponent.createButton("Create", () -> controller.createCharacter(
				nameInput.getText(),
				(String) heroClassInput.getSelectedItem()
		));
		JButton cancel = CustomComponent.createButton("Cancel", controller::cancelCreation);

		title.setHorizontalAlignment(SwingConstants.CENTER);
		nameError.setHorizontalAlignment(SwingConstants.CENTER);
		nameError.setFont(new Font("Arial", Font.ITALIC, 14));
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		nameInput.setHorizontalAlignment(SwingConstants.CENTER);
		heroClass.setHorizontalAlignment(SwingConstants.RIGHT);

		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		renderer.setBackground(new Color(50, 50, 50));
		renderer.setForeground(Color.WHITE);
		heroClassInput.setRenderer(renderer);

		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
		name.setFont(new Font("Arial", Font.PLAIN, 20));
		nameInput.setFont(new Font("Arial", Font.PLAIN, 20));
		heroClass.setFont(new Font("Arial", Font.PLAIN, 20));
		heroClassInput.setFont(new Font("Arial", Font.PLAIN, 20));

		title.setForeground(Color.WHITE);
		name.setForeground(Color.WHITE);
		heroClass.setForeground(Color.WHITE);

		nameInput.setBackground(new Color(50, 50, 50));
		nameInput.setForeground(Color.WHITE);
		nameInput.setCaretColor(Color.WHITE);
		nameInput.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

		heroClassInput.setBackground(new Color(50, 50, 50));
		heroClassInput.setForeground(Color.WHITE);
		heroClassInput.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(0, 0, 30, 0);
		add(title, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.ipady = 0;
		c.insets = new Insets(0, 0, 0, 0);
		add(name, c);

		c.gridx = 1;
		add(nameInput, c);

		c.gridx = 0;
		c.gridy = 2;
		add(heroClass, c);

		c.gridx = 1;
		add(heroClassInput, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.VERTICAL;
		c.ipady = 0;
		c.insets = new Insets(10, 0, 0, 0);
		add(nameError, c);

		c.gridy = 4;
		c.ipady = 10;
		c.insets = new Insets(20, 0, 0, 0);
		add(create, c);

		c.gridy = 5;
		c.insets = new Insets(0, 0, 0, 0);
		add(cancel, c);
	}

	public void setErrors(java.util.List<String> errors) {
		if (errors == null || errors.isEmpty()) {
			nameError.setText("");
			nameInput.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		} else {
			nameError.setText("<html><center>" + String.join("<br>", errors) + "</center></html>");
			nameError.setForeground(new Color(255, 120, 120));
			nameInput.setBorder(BorderFactory.createLineBorder(new Color(255, 120, 120), 2));
		}
	}
}
