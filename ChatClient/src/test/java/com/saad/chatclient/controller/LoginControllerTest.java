package com.saad.chatclient.controller;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class LoginControllerTest {

    private JFXPanel jfxPanel = new JFXPanel();

    private LoginController loginController = new LoginController();

    @Test
    void onLoginAction() {
        Platform.runLater(() -> {
            try {
                loginController.onLoginAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void onSignUpAction() {
        Platform.runLater(() -> {
            try {
                loginController.onSignUpAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}