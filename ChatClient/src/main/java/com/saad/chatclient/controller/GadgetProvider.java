package com.saad.chatclient.controller;

import com.saad.chatclient.ref;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        messageBox.setStyle("-fx-border-radius: 5 5 5 5;-fx-background-radius: 5 5 5 5;-fx-background-radius: 5px;-fx-background-color: " + color + ";");

        messageBox.getChildren().add(createLabel(username, pos, 16, ref.WhiteColor, true));
        messageBox.getChildren().add(createText(content, ref.WhiteColor));
        messageBox.getChildren().add(createLabel(date, pos, 14, ref.WhiteColor, false));
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
        textLabel.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
        textLabel.setFill(Color.web(color));
        textLabel.setWrappingWidth(300);
        return textLabel;
    }
}
