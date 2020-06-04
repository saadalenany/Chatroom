package com.saad.chatclient.sockets;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ChatSocketTest {

    @Test
    void connect() throws IOException {
        new ServerSocket(7003);
        System.out.println("Chat Server is up...");

        ChatSocket.connect();
        assertNotNull(ChatSocket.os, "Not connected");
        assertNotNull(ChatSocket.is, "Not connected");
    }
}