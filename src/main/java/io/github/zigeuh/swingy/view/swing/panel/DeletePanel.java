package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;

public class DeletePanel extends SlotListPanel {
	public DeletePanel(Controller controller) {
		super("Delete", controller::deleteSlot);
	}
}
