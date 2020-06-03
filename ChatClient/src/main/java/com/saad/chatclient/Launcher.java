package com.saad.chatclient;

import com.saad.chatclient.sockets.LoginSocket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ref.MainStage = primaryStage;
        LoginSocket.connect();
        ref.MainStage.setTitle("ChatRoom");
        ref.MainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(ref.LoginFxml)), 600, 500));
        ref.MainStage.show();
    }
}
