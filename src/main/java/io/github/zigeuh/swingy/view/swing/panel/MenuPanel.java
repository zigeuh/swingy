package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.model.SaveSlot;
import io.github.zigeuh.swingy.view.swing.panel.utils.CustomComponent;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

	private final SlotListPanel slotList;

	public MenuPanel(Controller controller) {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		JPanel header = new JPanel(new BorderLayout());
		header.setOpaque(false);
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		JLabel title = new JLabel("KINGDOM HEARTS: TESSELLATED HEARTS (Swingy)", SwingConstants.LEFT);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 32));
		header.add(title, BorderLayout.WEST);

		JButton delete = CustomComponent.createButton("Delete", controller::deleteMenu);
		header.add(delete, BorderLayout.EAST);

		add(header, BorderLayout.NORTH);

		slotList = new SlotListPanel(controller::chooseSlot);
		add(slotList, BorderLayout.CENTER);

		JButton quit = CustomComponent.createButton("Quit", controller::quit);

		JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
		footer.setOpaque(false);
		footer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		footer.add(quit);
		add(footer, BorderLayout.SOUTH);
	}

	public void setSlots(SaveSlot[] saves) {
		slotList.setSlots(saves);
	}
}
