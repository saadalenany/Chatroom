package com.saad.chatclient.controller;

import com.saad.chatclient.alert.Alerts;
import com.saad.chatclient.ref;
import com.saad.chatclient.sockets.ChatSocket;
import com.saad.chatclient.sockets.LoginSocket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.saad.chatclient.Protocols.*;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onLoginAction() throws IOException {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            Alerts.showErrorAlert("Error", "Wrong Cred.", "Empty username or password!");
            return;
        }
        System.out.println("Searching for username & password....");
        LoginSocket.os.writeByte(SEARCH_USERNAME_PASSWORD);
        LoginSocket.os.writeUTF(username.getText() + "," + password.getText());
        LoginSocket.os.flush();

        System.out.println("Retrieving username from server....");
        final String username = LoginSocket.is.readUTF();
        if (username.isEmpty()) {
            if (LoginSocket.is.readBoolean()) {
                Alerts.showErrorAlert("Error", "Wrong Action", "User is already logged...");
            } else {
                Alerts.showErrorAlert("Error", "Wrong Cred.", "Wrong username or password!");
            }
        } else {
            ref.user = username;
            System.out.println("User " + username + " is logged");
            //LOGIN
            login();
        }
    }

    public void onSignUpAction() throws IOException {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            Alerts.showErrorAlert("Error", "Wrong Cred.", "Empty username or password!");
            return;
        }
        System.out.println("Searching for username....");
        LoginSocket.os.writeByte(SEARCH_USERNAME);
        LoginSocket.os.writeUTF(username.getText());
        LoginSocket.os.flush();

        System.out.println("Retrieving username from server....");
        String retrievedUsername = LoginSocket.is.readUTF();
        if (retrievedUsername.isEmpty()) {
            System.out.println("Sending for creating a User to the server....");
            LoginSocket.os.writeByte(CREATE_USER);
            LoginSocket.os.writeUTF(username.getText() + "," + password.getText());
            LoginSocket.os.flush();

            System.out.println("Retrieving username of the created User from server....");
            retrievedUsername = LoginSocket.is.readUTF();
            if (retrievedUsername.isEmpty()) {
                Alerts.showErrorAlert("Error", "Wrong Cred.", "For some reason user wasn't created!");
            } else {
                ref.user = retrievedUsername;
                System.out.println("User " + retrievedUsername + " is logged");
                //LOGIN
                login();
            }
        } else {
            Alerts.showErrorAlert("Error", "Wrong Info.", String.format("Username [%s] already exists!", retrievedUsername));
        }
    }

    private void login() {
        ChatSocket.connect();
        try {
            ref.MainStage.setTitle(ref.user);
            ref.MainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(ref.MainFxml)), 600, 500));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
