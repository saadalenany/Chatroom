package com.saad.chatserver.listeners;

import com.saad.chatserver.model.User;
import com.saad.chatserver.ref;

import java.io.IOException;
import java.net.Socket;

class ChatListener {

    private User user;

    ChatListener(User user) {
        this.user = user;
    }

    void start() {
        try {
            // Accept client connection request
            // Get new Socket at Server.
            Socket socketOfServer = ref.listener.accept();
            System.out.println("A chat user has been accepted...");
            new ChatServiceThread(socketOfServer, user).start();
            System.out.println("A chat user thread has started...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
