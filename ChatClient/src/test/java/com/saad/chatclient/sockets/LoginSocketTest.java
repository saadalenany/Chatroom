package com.saad.chatclient.sockets;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginSocketTest {

    @Test
    void connect() throws IOException {
        new ServerSocket(7002);
        System.out.println("Login Server is up...");

        LoginSocket.connect();
        assertNotNull(LoginSocket.os, "Not connected");
        assertNotNull(LoginSocket.is, "Not connected");
    }
}