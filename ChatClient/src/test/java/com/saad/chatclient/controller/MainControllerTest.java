package com.saad.chatclient.controller;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class MainControllerTest {

    private JFXPanel jfxPanel = new JFXPanel();

    private MainController mainController = new MainController();

    @Test
    void onSendAction() {
        Platform.runLater(() -> {
            try {
                mainController.onSendAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void onEnterMessage() {
        Platform.runLater(() -> {
            try {
                mainController.onEnterMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}