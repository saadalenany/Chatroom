package com.saad.chatclient.controller;

import com.saad.chatclient.ref;
import com.saad.chatclient.sockets.ChatSocket;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.saad.chatclient.Protocols.*;

public class MainController implements Initializable {

    @FXML
    public TextField message;

    @FXML
    public VBox usersBox;

    @FXML
    public VBox chatBox;

    private GadgetProvider gadgetProvider;

    private boolean infinite = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gadgetProvider = new GadgetProvider();
        try {
            //REFRESH CONVERSATIONS
            ChatSocket.os.writeByte(REFRESH_CONVERSATION);
            System.out.println("Refreshing Conversation");
            if (ChatSocket.is.readByte() == REFRESH_CONVERSATION) {
                refreshConversations();
            }

            //REFRESH USERS
            ChatSocket.os.writeByte(REFRESH_USERS);
            System.out.println("Refreshing Users");
            if (ChatSocket.is.readByte() == REFRESH_USERS) {
                refreshUsers();
            }

            new Live().start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ref.MainStage.setOnCloseRequest(event -> {
            System.out.println("Closing...");
            try {
                endConversation();
                System.out.println("Closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
    }

    public void onSendAction() throws IOException {
        sendMessage();
    }

    public void onEnterMessage() throws IOException{
        sendMessage();
    }

    private void sendMessage() throws IOException {
        if (!message.getText().isEmpty()) {
            if (message.getText().equalsIgnoreCase("bye bye")) {
                endConversation();
            } else {
                ChatSocket.os.writeByte(NEW_MESSAGE);
                ChatSocket.os.writeUTF(message.getText());
                ChatSocket.os.writeUTF(LocalDateTime.now().toString().replaceAll("T", " "));
                ChatSocket.os.flush();
            }
            message.setText("");
        }
    }

    private void updatePublicMessage() throws IOException {
        final String username = ChatSocket.is.readUTF();
        final String content = ChatSocket.is.readUTF();
        final String date = ChatSocket.is.readUTF();
        if (ref.user.equals(username)) {
            Platform.runLater(() -> chatBox.getChildren().add(gadgetProvider.createMessageElement(username, content, date, Pos.CENTER_RIGHT, ref.GreenColor)));
        } else {
            Platform.runLater(() -> chatBox.getChildren().add(gadgetProvider.createMessageElement(username, content, date, Pos.CENTER_LEFT, ref.BlueColor)));
        }
    }

    private void refreshUsers() throws IOException {
        final int size = ChatSocket.is.readInt();
        System.out.println("usersLength " + size);
        Platform.runLater(() -> usersBox.getChildren().clear());
        for (int i = 0; i < size; i++) {
            System.out.println("starting with " + i);
            final String a_user = ChatSocket.is.readUTF();
            Platform.runLater(() -> usersBox.getChildren().add(gadgetProvider.createLabel(a_user, Pos.CENTER, 26, ref.BlackColor, false)));
        }
    }

    private void refreshConversations() throws IOException {
        final int length = ChatSocket.is.readInt();
        System.out.println("messagesLength " + length);
        Platform.runLater(() -> chatBox.getChildren().clear());
        for (int i = 0; i < length; i++) {
            System.out.println("starting with " + i);
            final String username = ChatSocket.is.readUTF();
            final String content = ChatSocket.is.readUTF();
            final String date = ChatSocket.is.readUTF();
            if (ref.user.equals(username)) {
                Platform.runLater(() -> chatBox.getChildren().add(gadgetProvider.createMessageElement(username, content, date, Pos.CENTER_RIGHT, ref.GreenColor)));
            } else {
                Platform.runLater(() -> chatBox.getChildren().add(gadgetProvider.createMessageElement(username, content, date, Pos.CENTER_LEFT, ref.BlueColor)));
            }
        }
    }

    private void endConversation() throws IOException {
        ChatSocket.os.writeByte(END_CONVERSATION);
        infinite = false;
        ChatSocket.os.close();
        ChatSocket.is.close();
        System.exit(0);
    }

    private class Live extends Thread {
        @Override
        public void run() {
            System.out.println("LIVE...");
            try {
                while (infinite) {
                    System.out.println("waiting for protocol.");
                    byte protocol = ChatSocket.is.readByte();
                    System.out.println("Received " + protocol);

                    switch (protocol) {
                        case REFRESH_CONVERSATION:
                            refreshConversations();
                            break;
                        case REFRESH_USERS:
                            refreshUsers();
                            break;
                        case PUBLIC_MESSAGE:
                            System.out.println("PUBLIC_MESSAGE");
                            updatePublicMessage();
                            break;
                        default:
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

}
