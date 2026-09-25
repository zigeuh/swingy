package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.BattleResult;
import io.github.zigeuh.swingy.model.RoundLog;
import io.github.zigeuh.swingy.view.swing.panel.utils.CustomComponent;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BattleRecapPanel extends JPanel {

	private final Controller controller;
	private final JLabel title = new JLabel("Battle", SwingConstants.LEFT);
	private final JLabel summary = new JLabel("", SwingConstants.CENTER);
	private final DefaultListModel<String> listModel = new DefaultListModel<>();
	private final JList<String> logList = new JList<>(listModel);
	private final JButton nextButton = CustomComponent.createButton("Next >", this::showNext);
	private final JButton skipButton = CustomComponent.createButton("Skip >>", this::showAll);
	private final JButton continueButton;

	private BattleResult current;
	private int shown;

	public BattleRecapPanel(Controller controller) {
		this.controller = controller;
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

		continueButton = CustomComponent.createButton("Continue", controller::afterBattle);

		JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		footer.setOpaque(false);
		footer.setBackground(Color.BLACK);
		footer.add(nextButton);
		footer.add(skipButton);
		footer.add(continueButton);
		box.add(footer, BorderLayout.SOUTH);
	}

	public void setResult(BattleResult result) {
		this.current = result;
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
			summary.setText("<html><center>XP gained : <font color='#78ff78'>"
					+ current.xpGained() + "</font>"
					+ "<br>Hero HP left : <font color='#78ff78'>" + current.heroHpLeft()
					+ "</font></center></html>");
		}
	}

	private static String format(RoundLog round) {
		return "<html><font color='#3caec8'><b>Round " + round.round() + "</b></font> - <b>"
				+ round.attacker() + "</b> deals <font color='#ffb14e'><b>" + round.damage()
				+ " dmg</b></font> <font color='gray'>(hero: " + round.heroHpLeft()
				+ ", enemy: " + round.enemyHpLeft() + ")</font></html>";
	}
}
