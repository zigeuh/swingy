package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.enemy.Enemy;
import io.github.zigeuh.swingy.view.swing.panel.utils.CustomComponent;

import javax.swing.*;
import java.awt.*;

public class StartBattlePanel extends JPanel {
	private final Controller controller;
	private final JLabel info;

	public StartBattlePanel(Controller controller) {
		this.controller = controller;
		setBackground(Color.BLACK);

		JLabel title = new JLabel("An enemy is approaching...");

		info = new JLabel("");

		JButton fight = CustomComponent.createButton("Fight", controller::fight);
		JButton escape = CustomComponent.createButton("Try to escape (50%)", controller::escape);

		title.setHorizontalAlignment(SwingConstants.CENTER);
		info.setHorizontalAlignment(SwingConstants.CENTER);

		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
		info.setFont(new Font("Arial", Font.PLAIN, 20));

		title.setForeground(Color.WHITE);
		info.setForeground(Color.WHITE);

		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 20, 0);
		add(title, c);

		c.gridy = 1;
		c.insets = new Insets(0, 0, 5, 0);
		add(info, c);

		c.gridy = 2;
		c.ipady = 10;
		c.insets = new Insets(0, 0, 0, 0);
		add(fight, c);

		c.gridy = 3;
		add(escape, c);
	}

	public void setEnemy(Enemy enemy) {
		info.setText(enemy.getName() + "  (HP " + enemy.getHp() + " / ATK " + enemy.getAttack() + " / DEF " + enemy.getDefense() + ")");
	}
}
