package io.github.zigeuh.swingy;

import io.github.zigeuh.swingy.controller.Controller;
import io.github.zigeuh.swingy.view.console.ConsoleView;
import io.github.zigeuh.swingy.view.swing.SwingView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        String mode = args.length > 0 ? args[0].toLowerCase() : "gui";
        switch (mode) {
//            case "console" -> {
//                Controller controller = new Controller();
//                controller.setView(new ConsoleView(controller));
//                controller.start();
//            }
            case "gui" -> SwingUtilities.invokeLater(() -> {
                Controller controller = new Controller();
                controller.setView(new SwingView(controller));
                controller.start();
            });
            default -> {
                System.err.println("Usage: java -jar swingy.jar [console|gui]");
                System.exit(1);
            }
        }
    }
}
