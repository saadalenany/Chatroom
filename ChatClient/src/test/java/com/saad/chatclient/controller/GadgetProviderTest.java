package com.saad.chatclient.controller;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class GadgetProviderTest {

    protected JFXPanel jfxPanel = new JFXPanel();

    private GadgetProvider gadgetProvider = new GadgetProvider();

    @Test
    void createMessageElement() {
        final VBox messageElement = gadgetProvider.createMessageElement(anyString(), anyString(), anyString(), any(Pos.class), anyString());
        assertEquals(3, messageElement.getChildren().size());
    }

    @Test
    void createLabel() {
        final Label label = gadgetProvider.createLabel(anyString(), any(Pos.class), any(Integer.class), "#000000", any(Boolean.class));
        assertNotNull(label);
        assertNotNull(label.getText());
    }
}