package io.github.zigeuh.swingy.view.swing.panel;

import io.github.zigeuh.swingy.controller.Controller;

public class SavePanel extends SlotListPanel {
	public SavePanel(Controller controller) {
		super("Save", controller::saveSlot);
	}
}
