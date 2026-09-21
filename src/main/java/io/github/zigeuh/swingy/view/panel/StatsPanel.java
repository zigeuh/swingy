package io.github.zigeuh.swingy.view.panel;

import io.github.zigeuh.swingy.model.hero.Hero;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

	private final JLabel nameValue = new JLabel("", SwingConstants.RIGHT);
	private final JLabel classValue = new JLabel("", SwingConstants.RIGHT);
	private final JLabel levelValue = new JLabel("", SwingConstants.RIGHT);
	private final JLabel xpValue = new JLabel("", SwingConstants.RIGHT);
	private final JLabel hpValue = new JLabel("", SwingConstants.RIGHT);
	private final JProgressBar xpBar = new JProgressBar(0, 100);
	private final JLabel attackValue = new JLabel("", SwingConstants.RIGHT);
	private final JLabel defenseValue = new JLabel("", SwingConstants.RIGHT);
	private final JProgressBar hpBar = new JProgressBar(0, 100);
	private final JLabel spriteLabel = new JLabel("Sprite", SwingConstants.CENTER);

	public StatsPanel() {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(280, 0));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel header = new JLabel("Stats", SwingConstants.CENTER);
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
		add(header, BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setOpaque(false);
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

		spriteLabel.setPreferredSize(new Dimension(180, 180));
		spriteLabel.setMaximumSize(new Dimension(180, 180));
		spriteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		spriteLabel.setOpaque(true);
		spriteLabel.setBackground(Color.BLACK);
		spriteLabel.setForeground(Color.GRAY);
		spriteLabel.setFont(new Font("Arial", Font.ITALIC, 16));
		spriteLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		center.add(Box.createVerticalStrut(10));
		center.add(spriteLabel);

		center.add(Box.createVerticalStrut(15));
		center.add(statRow("Name", nameValue));
		center.add(statRow("Class", classValue));
		center.add(statRow("Level", levelValue));
		center.add(statRow("XP", xpValue));

		xpBar.setStringPainted(false);
		xpBar.setBackground(new Color(50, 50, 50));
		xpBar.setForeground(new Color(60, 174, 200));
		xpBar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		xpBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		xpBar.setMaximumSize(new Dimension(220, 12));
		center.add(xpBar);
		center.add(Box.createVerticalStrut(5));

		center.add(statRow("HP", hpValue));

		hpBar.setStringPainted(false);
		hpBar.setBackground(new Color(50, 50, 50));
		hpBar.setForeground(new Color(60, 200, 60));
		hpBar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		hpBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		hpBar.setMaximumSize(new Dimension(220, 12));
		center.add(hpBar);

		center.add(Box.createVerticalStrut(5));
		center.add(statRow("Attack", attackValue));
		center.add(statRow("Defense", defenseValue));

		add(center, BorderLayout.CENTER);
	}

	public void setHero(Hero hero) {
		var url = StatsPanel.class.getResource(hero.getHeroClass().getSpriteFolder() + "/portrait.png");
		setSpriteIcon(new ImageIcon(url));

		nameValue.setText(hero.getName());
		classValue.setText(hero.getHeroClass().getDisplayName());
		levelValue.setText(String.valueOf(hero.getLevel()));
		xpValue.setText(String.valueOf(hero.getXp()));
		hpValue.setText(hero.getHp() + " / " + hero.getMaxHp());
		attackValue.setText(String.valueOf(hero.getAttack()));
		defenseValue.setText(String.valueOf(hero.getDefense()));
		xpBar.setMaximum(Math.max(1, hero.getRequiredXp()));
		xpBar.setValue(Math.clamp(hero.getXp(), 0, hero.getRequiredXp()));
		hpBar.setMaximum(Math.max(1, hero.getMaxHp()));
		hpBar.setValue(Math.clamp(hero.getHp(), 0, hero.getMaxHp()));
	}

	public void setSpriteIcon(ImageIcon icon) {
		if (icon == null) {
			spriteLabel.setIcon(null);
			spriteLabel.setText("Sprite");
		} else {
			spriteLabel.setText("");
			int box = 180;
			int w = Math.max(1, icon.getIconWidth());
			int h = Math.max(1, icon.getIconHeight());
			double scale = Math.min((double) box / w, (double) box / h);
			int dw = Math.max(1, (int) Math.round(w * scale));
			int dh = Math.max(1, (int) Math.round(h * scale));
			Image scaled = icon.getImage().getScaledInstance(dw, dh, Image.SCALE_SMOOTH);
			spriteLabel.setIcon(new ImageIcon(scaled));
		}
	}

	private static JPanel statRow(String key, JLabel value) {
		JPanel row = new JPanel(new BorderLayout());
		row.setOpaque(false);
		row.setMaximumSize(new Dimension(240, 22));

		JLabel keyLabel = new JLabel(key);
		keyLabel.setForeground(Color.GRAY);
		keyLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		value.setForeground(Color.WHITE);
		value.setFont(new Font("Arial", Font.BOLD, 14));

		row.add(keyLabel, BorderLayout.WEST);
		row.add(value, BorderLayout.EAST);
		return row;
	}
}
