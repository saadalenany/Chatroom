package com.saad.chatserver.listeners;

import com.saad.chatserver.service.CsvFiles;
import com.saad.chatserver.service.Protocols;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginListener {

    public void start() {
        ServerSocket listener = null;

        int clientNumber = 0;
        // Try to open a server socket on port 7002
        try {
            listener = new ServerSocket(Protocols.SignInOutPort);
            System.out.println("Login Server is up...");
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        CsvFiles.prepareUsersFile();

        System.out.println("Server is waiting to accept user...");
        try {
            while (true) {
                // Accept client connection request
                // Get new Socket at Server.
                Socket socketOfServer = listener.accept();
                System.out.println("A user has been accepted...");
                new LoginServiceThread(socketOfServer, clientNumber++).start();
                System.out.println("A user thread has started...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                listener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
