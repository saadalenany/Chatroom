package com.saad.chatclient.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class GadgetProvider {

    VBox createMessageElement(String username, String content, String date, Pos pos, String color) {
        VBox messageBox = new VBox();
        messageBox.setAlignment(pos);
        messageBox.setSpacing(3);
        messageBox.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        messageBox.setFillWidth(true);
        messageBox.setPrefWidth(Double.MAX_VALUE);
        messageBox.setStyle("-fx-border-radius: 5 5 5 5;-fx-background-radius: 5 5 5 5;-fx-background-radius: 5px;-fx-border-color: " + color + ";");

        messageBox.getChildren().add(createLabel(username, pos, 15, color, true));
        messageBox.getChildren().add(createText(content, color));
        messageBox.getChildren().add(createLabel(date, pos, 12, "#808080", false));
        return messageBox;
    }

    Label createLabel(String text, Pos pos, int size, String color, boolean bold) {
        Label textLabel = new Label(text);
        textLabel.setAlignment(pos);
        textLabel.setFont(new Font("Arial", size));
        textLabel.setTextFill(Color.web(color));
        if (bold) {
            textLabel.setStyle("-fx-font-weight: bold;");
        }
        return textLabel;
    }

    private Text createText(String text, String color) {
        Text textLabel = new Text(text);
        textLabel.setTextAlignment(TextAlignment.CENTER);
        textLabel.setFont(new Font("Arial", 16));
        textLabel.setFill(Color.web(color));
        textLabel.setWrappingWidth(300);
        return textLabel;
    }
}
