package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.artifact.Artifact;
import io.github.zigeuh.swingy.model.artifact.ArtifactType;
import io.github.zigeuh.swingy.view.swing.panel.utils.CustomComponent;

import javax.swing.*;
import java.awt.*;

public class LootPanel extends JPanel {

	private final Controller controller;

	private final JLabel currentSprite = new JLabel("Sprite", SwingConstants.CENTER);
	private final JLabel currentName = new JLabel("", SwingConstants.CENTER);
	private final JLabel currentStat = new JLabel("", SwingConstants.CENTER);
	private final JLabel currentDelta = new JLabel("", SwingConstants.CENTER);

	private final JLabel lootSprite = new JLabel("Sprite", SwingConstants.CENTER);
	private final JLabel lootName = new JLabel("", SwingConstants.CENTER);
	private final JLabel lootStat = new JLabel("", SwingConstants.CENTER);
	private final JLabel lootDelta = new JLabel("", SwingConstants.CENTER);

	public LootPanel(Controller controller) {
		this.controller = controller;
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		JPanel header = new JPanel(new BorderLayout());
		header.setOpaque(false);
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		JLabel title = new JLabel("Loot !", SwingConstants.LEFT);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 32));
		header.add(title, BorderLayout.WEST);

		add(header, BorderLayout.NORTH);

		JPanel wrapper = new JPanel(new GridBagLayout());
		wrapper.setOpaque(false);
		wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel box = new JPanel(new BorderLayout(0, 10));
		box.setBackground(Color.BLACK);
		box.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 1),
				BorderFactory.createEmptyBorder(15, 15, 15, 15)));
		box.setPreferredSize(new Dimension(500, 450));

		JPanel cards = new JPanel(new GridLayout(1, 3, 10, 0));
		cards.setOpaque(false);

		cards.add(lootCard("Equipped", currentSprite, currentName, currentStat, currentDelta));

		JLabel arrow = new JLabel("→", SwingConstants.CENTER);
		arrow.setForeground(Color.WHITE);
		arrow.setFont(new Font("Arial", Font.BOLD, 40));
		cards.add(arrow);

		cards.add(lootCard("Dropped", lootSprite, lootName, lootStat, lootDelta));

		box.add(cards, BorderLayout.CENTER);

		JButton keepButton = CustomComponent.createButton("Keep", controller::keepLoot);
		JButton dropButton = CustomComponent.createButton("Drop", controller::dropLoot);

		JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		footer.setOpaque(false);
		footer.setBackground(Color.BLACK);
		footer.add(keepButton);
		footer.add(dropButton);
		box.add(footer, BorderLayout.SOUTH);

		wrapper.add(box);
		add(wrapper, BorderLayout.CENTER);
	}

	public void setLoot(Artifact current, Artifact loot) {
		setSlotIcon(lootSprite, loot);
		lootName.setText(loot.getName());
		lootStat.setText(statLine(loot));

		if (current == null) {
			setSlotIcon(currentSprite, null);
			currentName.setText("Empty slot");
			currentStat.setText(statName(loot.getType()) + " : -");
			currentDelta.setText("");
			lootDelta.setText("NEW");
			lootDelta.setForeground(new Color(120, 255, 120));
		} else {
			setSlotIcon(currentSprite, current);
			currentName.setText(current.getName());
			currentStat.setText(statLine(current));
			int diff = loot.bonus() - current.bonus();
			if (diff > 0) {
				lootDelta.setText("+" + diff + " better");
				lootDelta.setForeground(new Color(120, 255, 120));
				currentDelta.setText("");
			} else if (diff < 0) {
				lootDelta.setText(diff + " worse");
				lootDelta.setForeground(new Color(255, 120, 120));
				currentDelta.setText("");
			} else {
				lootDelta.setText("equal");
				lootDelta.setForeground(Color.GRAY);
				currentDelta.setText("");
			}
		}
	}

	private static void setSlotIcon(JLabel sprite, Artifact artifact) {
		ImageIcon icon = artifact == null ? null : scaledIcon(artifact.getTemplate().getSprite(), 120);
		if (icon == null) {
			sprite.setIcon(null);
			sprite.setText("Sprite");
		} else {
			sprite.setText("");
			sprite.setIcon(icon);
		}
	}

	private static ImageIcon scaledIcon(String path, int box) {
		var url = LootPanel.class.getResource(path);
		if (url == null) return null;
		ImageIcon raw = new ImageIcon(url);
		int w = Math.max(1, raw.getIconWidth());
		int h = Math.max(1, raw.getIconHeight());
		double scale = Math.min((double) box / w, (double) box / h);
		int dw = Math.max(1, (int) Math.round(w * scale));
		int dh = Math.max(1, (int) Math.round(h * scale));
		return new ImageIcon(raw.getImage().getScaledInstance(dw, dh, Image.SCALE_SMOOTH));
	}

	private static JPanel lootCard(String header, JLabel sprite, JLabel name, JLabel stat, JLabel delta) {
		JPanel card = new JPanel();
		card.setOpaque(false);
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

		JLabel headerLabel = new JLabel(header, SwingConstants.CENTER);
		headerLabel.setForeground(Color.GRAY);
		headerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
		headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		sprite.setPreferredSize(new Dimension(120, 120));
		sprite.setMaximumSize(new Dimension(120, 120));
		sprite.setAlignmentX(Component.CENTER_ALIGNMENT);
		sprite.setOpaque(true);
		sprite.setBackground(Color.BLACK);
		sprite.setForeground(Color.GRAY);
		sprite.setFont(new Font("Arial", Font.ITALIC, 14));
		sprite.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

		name.setForeground(Color.WHITE);
		name.setFont(new Font("Arial", Font.BOLD, 14));
		name.setAlignmentX(Component.CENTER_ALIGNMENT);

		stat.setForeground(Color.WHITE);
		stat.setFont(new Font("Arial", Font.PLAIN, 13));
		stat.setAlignmentX(Component.CENTER_ALIGNMENT);

		delta.setFont(new Font("Arial", Font.BOLD, 13));
		delta.setAlignmentX(Component.CENTER_ALIGNMENT);

		card.add(Box.createVerticalStrut(8));
		card.add(headerLabel);
		card.add(Box.createVerticalStrut(8));
		card.add(sprite);
		card.add(Box.createVerticalStrut(8));
		card.add(name);
		card.add(stat);
		card.add(delta);
		card.add(Box.createVerticalGlue());
		return card;
	}

	private static String statLine(Artifact artifact) {
		return statName(artifact.getType()) + " : +" + artifact.bonus();
	}

	private static String statName(ArtifactType type) {
		return switch (type) {
			case WEAPON -> "Attack";
			case ARMOR -> "Defense";
			case HELM -> "HP";
		};
	}
}
