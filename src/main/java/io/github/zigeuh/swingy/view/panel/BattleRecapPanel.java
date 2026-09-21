package io.github.zigeuh.swingy.view.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.BattleResult;
import io.github.zigeuh.swingy.model.RoundLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

public class BattleRecapPanel extends JPanel {

	private final Controller controller;
	private final JLabel title = new JLabel("Battle", SwingConstants.LEFT);
	private final JLabel summary = new JLabel("", SwingConstants.CENTER);
	private final DefaultListModel<String> listModel = new DefaultListModel<>();
	private final JList<String> logList = new JList<>(listModel);
	private final JButton nextButton = new JButton("Next >");
	private final JButton skipButton = new JButton("Skip >>");
	private final JButton continueButton = new JButton("Continue");

	private BattleResult current;
	private int shown;

	public BattleRecapPanel(Controller controller) {
		this.controller = Objects.requireNonNull(controller);
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		JPanel header = new JPanel(new BorderLayout());
		header.setOpaque(false);
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 32));
		header.add(title, BorderLayout.WEST);

		add(header, BorderLayout.NORTH);

		JPanel wrapper = new JPanel(new GridBagLayout());
		wrapper.setOpaque(false);
		wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel box = new JPanel(new BorderLayout(0, 5));
		box.setBackground(Color.BLACK);
		box.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 1),
				BorderFactory.createEmptyBorder(15, 15, 15, 15)));
		box.setPreferredSize(new Dimension(500, 450));

		summary.setForeground(Color.WHITE);
		summary.setFont(new Font("Arial", Font.PLAIN, 16));
		box.add(summary, BorderLayout.NORTH);

		logList.setBackground(Color.BLACK);
		logList.setForeground(Color.WHITE);
		logList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
		logList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(logList);
		scroll.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		scroll.getViewport().setBackground(Color.BLACK);
		box.add(scroll, BorderLayout.CENTER);

		wrapper.add(box);
		add(wrapper, BorderLayout.CENTER);

		styleButton(nextButton);
		styleButton(skipButton);
		styleButton(continueButton);
		nextButton.addActionListener(e -> showNext());
		skipButton.addActionListener(e -> showAll());
		continueButton.addActionListener(e -> this.controller.afterBattle());

		JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		footer.setOpaque(false);
		footer.setBackground(Color.BLACK);
		footer.add(nextButton);
		footer.add(skipButton);
		footer.add(continueButton);
		box.add(footer, BorderLayout.SOUTH);
	}

	public void setResult(BattleResult result) {
		this.current = Objects.requireNonNull(result);
		this.shown = 0;
		title.setText(result.victory() ? "Victory !" : "Defeat...");
		title.setForeground(result.victory() ? new Color(120, 255, 120) : new Color(255, 120, 120));
		summary.setText("Fighting...");
		listModel.clear();
		showNext();
	}

	private void showNext() {
		if (current == null) {
			return;
		}
		List<RoundLog> rounds = current.rounds();
		if (shown < rounds.size()) {
			listModel.addElement(format(rounds.get(shown)));
			shown++;
			logList.ensureIndexIsVisible(listModel.size() - 1);
		}
		refreshFooter();
	}

	private void showAll() {
		if (current == null) {
			return;
		}
		List<RoundLog> rounds = current.rounds();
		while (shown < rounds.size()) {
			listModel.addElement(format(rounds.get(shown)));
			shown++;
		}
		if (!listModel.isEmpty()) {
			logList.ensureIndexIsVisible(listModel.size() - 1);
		}
		refreshFooter();
	}

	private void refreshFooter() {
		boolean done = current != null && shown >= current.rounds().size();
		nextButton.setVisible(!done);
		skipButton.setVisible(!done);
		continueButton.setVisible(done);
		if (done && current != null) {
			summary.setText("<html><center>XP gained : " + current.xpGained()
					+ "<br>Hero HP left : " + current.heroHpLeft() + "</center></html>");
		}
	}

	private static String format(RoundLog round) {
		return "Round " + round.round() + " - " + round.attacker()
				+ " deals " + round.damage() + " dmg"
				+ " (hero: " + round.heroHpLeft()
				+ ", enemy: " + round.enemyHpLeft() + ")";
	}

	private static void styleButton(JButton button) {
		button.setFont(new Font("Arial", Font.BOLD, 20));
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(50, 50, 50));
		button.setOpaque(true);
		button.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(41, 98, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(new Color(50, 50, 50));
			}
		});
	}
}
