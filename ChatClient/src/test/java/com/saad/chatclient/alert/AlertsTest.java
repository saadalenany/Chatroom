package com.saad.chatclient.alert;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class AlertsTest {

    protected JFXPanel jfxPanel = new JFXPanel();

    @BeforeEach
    void setUp() {
    }

    @Test
    void showErrorAlert() {
        Platform.runLater( () -> Alerts.showErrorAlert(anyString(), anyString(), anyString()));
    }
}