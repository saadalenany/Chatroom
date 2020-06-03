package com.saad.chatserver;

import com.saad.chatserver.listeners.LoginListener;
import com.saad.chatserver.service.Protocols;

import java.io.IOException;
import java.net.ServerSocket;

public class Launcher {

    public static void main(String[] args) {
        //start the Main Listener
        startMainServer();

        //start the Login listener
        LoginListener loginListener = new LoginListener();
        loginListener.start();
    }

    private static void startMainServer() {
        // Try to open a server socket on port 7003
        try {
            ref.listener = new ServerSocket(Protocols.ChatPort);
            System.out.println("Chat Server is up...");
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
}
