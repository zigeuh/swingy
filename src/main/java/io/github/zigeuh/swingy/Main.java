package io.github.zigeuh.swingy;

import io.github.zigeuh.swingy.view.View;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World");

        SwingUtilities.invokeLater(() -> {
            View view = new View();
            view.setVisible(true);
        });
    }
}
