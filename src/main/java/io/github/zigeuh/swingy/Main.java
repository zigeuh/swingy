package io.github.zigeuh.swingy;

import io.github.zigeuh.swingy.controller.Controller;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Controller().start());
    }
}
