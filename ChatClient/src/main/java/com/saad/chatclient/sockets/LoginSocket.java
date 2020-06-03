package com.saad.chatclient.sockets;

import com.saad.chatclient.alert.Alerts;
import com.saad.chatclient.Protocols;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginSocket {

    public static DataOutputStream os;
    public static DataInputStream is;

    public static void connect() {
        try {
            // Send a request to connect to the server is listening
            // on machine 'localhost' port 7002.
            System.out.println("Connection to localhost:7002....");
            Socket socketOfClient = new Socket(Protocols.ServerAddress, Protocols.SignInOutPort);

            // Create output stream at the client (to send data to the server)
            os = new DataOutputStream(socketOfClient.getOutputStream());

            // Input stream at Client (Receive data from the server).
            is = new DataInputStream(socketOfClient.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + Protocols.ServerAddress);
            Alerts.showErrorAlert("Error", "Network issue.", String.format("Unknown Server Host [%s]!", Protocols.ServerAddress));
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + Protocols.ServerAddress);
            Alerts.showErrorAlert("Error", "Network issue.", String.format("Couldn't establish connection to [%s]", Protocols.ServerAddress));
            System.exit(0);
        }
    }
}
