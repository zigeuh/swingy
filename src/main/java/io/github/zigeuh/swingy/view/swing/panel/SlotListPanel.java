package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.view.swing.panel.utils.CustomComponent;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntConsumer;

public class SlotListPanel extends JPanel {
	private final JPanel slotsList = new JPanel(new GridLayout(3, 1, 0, 10));
	private final IntConsumer onSlot;

	public SlotListPanel(String title, IntConsumer onSlot) {
		this(onSlot);

		JLabel titleLabel = new JLabel(title, SwingConstants.LEFT);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 32));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
		add(titleLabel, BorderLayout.NORTH);
	}

	public SlotListPanel(IntConsumer onSlot) {
		this.onSlot = onSlot;

		setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		slotsList.setOpaque(false);
		slotsList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(slotsList, BorderLayout.CENTER);
	}

	public void setSlots(SaveSlot[] saves) {
		slotsList.removeAll();
		for (SaveSlot save : saves) {
			slotsList.add(CustomComponent.createSlot(save, () -> onSlot.accept(save.getSlotNumber())));
		}
		slotsList.revalidate();
		slotsList.repaint();
	}
}
