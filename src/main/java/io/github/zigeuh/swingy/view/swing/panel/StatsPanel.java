package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.model.Map;
import io.github.zigeuh.swingy.model.artifact.Artifact;
import io.github.zigeuh.swingy.model.artifact.ArtifactType;
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
	private final JLabel mapSize = new JLabel("", SwingConstants.RIGHT);
	private final JLabel currentCoordinates = new JLabel("", SwingConstants.RIGHT);

	private final JLabel weaponSprite = new JLabel("Sprite", SwingConstants.CENTER);
	private final JLabel weaponName = new JLabel("", SwingConstants.LEFT);
	private final JLabel weaponBonus = new JLabel("", SwingConstants.RIGHT);
	private final JLabel armorSprite = new JLabel("Sprite", SwingConstants.CENTER);
	private final JLabel armorName = new JLabel("", SwingConstants.LEFT);
	private final JLabel armorBonus = new JLabel("", SwingConstants.RIGHT);
	private final JLabel helmSprite = new JLabel("Sprite", SwingConstants.CENTER);
	private final JLabel helmName = new JLabel("", SwingConstants.LEFT);
	private final JLabel helmBonus = new JLabel("", SwingConstants.RIGHT);

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
		center.add(statRow("Map size", mapSize));
		center.add(statRow("Coordinates", currentCoordinates));

		center.add(Box.createVerticalStrut(10));
		JLabel artifactsHeader = new JLabel("Artifacts", SwingConstants.CENTER);
		artifactsHeader.setForeground(Color.WHITE);
		artifactsHeader.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
		artifactsHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		center.add(artifactsHeader);
		center.add(Box.createVerticalStrut(10));
		center.add(artifactRow("Weapon", weaponSprite, weaponName, weaponBonus));
		center.add(Box.createVerticalStrut(10));
		center.add(artifactRow("Armor", armorSprite, armorName, armorBonus));
		center.add(Box.createVerticalStrut(10));
		center.add(artifactRow("Helm", helmSprite, helmName, helmBonus));

		add(center, BorderLayout.CENTER);
	}

	public void setHero(Hero hero, Map map) {
		var url = StatsPanel.class.getResource(hero.getHeroClass().getSpriteFolder() + "/portrait.png");
		setSpriteIcon(new ImageIcon(url));

		nameValue.setText(hero.getName());
		classValue.setText(hero.getHeroClass().getDisplayName());
		levelValue.setText(String.valueOf(hero.getLevel()));
		xpValue.setText(hero.getXp() + " / " + hero.getRequiredXp());
		hpValue.setText(hero.getHp() + " / " + hero.getMaxHp());
		attackValue.setText(String.valueOf(hero.getAttack()));
		defenseValue.setText(String.valueOf(hero.getDefense()));
		xpBar.setMaximum(Math.max(1, hero.getRequiredXp()));
		xpBar.setValue(Math.clamp(hero.getXp(), 0, hero.getRequiredXp()));
		hpBar.setMaximum(Math.max(1, hero.getMaxHp()));
		hpBar.setValue(Math.clamp(hero.getHp(), 0, hero.getMaxHp()));
		mapSize.setText(map.getGrid()[0].length + "x" + map.getGrid().length);
		currentCoordinates.setText("x: " + map.getHeroPos().x() + " | y: " + map.getHeroPos().y());
		setArtifactSlot(ArtifactType.WEAPON, hero.getWeapon(), weaponSprite, weaponName, weaponBonus);
		setArtifactSlot(ArtifactType.ARMOR, hero.getArmor(), armorSprite, armorName, armorBonus);
		setArtifactSlot(ArtifactType.HELM, hero.getHelm(), helmSprite, helmName, helmBonus);
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

	private static JPanel artifactRow(String key, JLabel sprite, JLabel name, JLabel bonus) {
		JPanel row = new JPanel(new BorderLayout(8, 0));
		row.setOpaque(false);
		row.setMaximumSize(new Dimension(240, 52));

		sprite.setPreferredSize(new Dimension(48, 48));
		sprite.setMaximumSize(new Dimension(48, 48));
		sprite.setOpaque(true);
		sprite.setBackground(Color.BLACK);
		sprite.setForeground(Color.GRAY);
		sprite.setFont(new Font("Arial", Font.ITALIC, 10));
		sprite.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

		JPanel text = new JPanel(new BorderLayout());
		text.setOpaque(false);

		JLabel keyLabel = new JLabel(key);
		keyLabel.setForeground(Color.GRAY);
		keyLabel.setFont(new Font("Arial", Font.PLAIN, 12));

		name.setForeground(Color.WHITE);
		name.setFont(new Font("Arial", Font.BOLD, 12));

		text.add(keyLabel, BorderLayout.NORTH);
		text.add(name, BorderLayout.SOUTH);

		bonus.setForeground(new Color(120, 255, 120));
		bonus.setFont(new Font("Arial", Font.BOLD, 12));

		row.add(sprite, BorderLayout.WEST);
		row.add(text, BorderLayout.CENTER);
		row.add(bonus, BorderLayout.EAST);
		return row;
	}

	private static void setArtifactSlot(ArtifactType type, Artifact artifact, JLabel sprite, JLabel name, JLabel bonus) {
		if (artifact == null) {
			sprite.setIcon(null);
			sprite.setText("—");
			name.setText("Empty");
			name.setForeground(Color.GRAY);
			bonus.setText("");
		} else {
			ImageIcon icon = scaledIcon(artifact.getTemplate().getSprite(), 48);
			if (icon == null) {
				sprite.setIcon(null);
				sprite.setText("Sprite");
			} else {
				sprite.setText("");
				sprite.setIcon(icon);
			}
			name.setText(artifact.getName());
			name.setForeground(Color.WHITE);
			bonus.setText(statName(type) + " +" + artifact.bonus());
			bonus.setToolTipText(statName(type) + " +" + artifact.bonus());
		}
	}

	private static ImageIcon scaledIcon(String path, int box) {
		var url = StatsPanel.class.getResource(path);
		if (url == null) return null;
		ImageIcon raw = new ImageIcon(url);
		int w = Math.max(1, raw.getIconWidth());
		int h = Math.max(1, raw.getIconHeight());
		double scale = Math.min((double) box / w, (double) box / h);
		int dw = Math.max(1, (int) Math.round(w * scale));
		int dh = Math.max(1, (int) Math.round(h * scale));
		return new ImageIcon(raw.getImage().getScaledInstance(dw, dh, Image.SCALE_SMOOTH));
	}

	private static String statName(ArtifactType type) {
		return switch (type) {
			case WEAPON -> "Attack";
			case ARMOR -> "Defense";
			case HELM -> "HP";
		};
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
