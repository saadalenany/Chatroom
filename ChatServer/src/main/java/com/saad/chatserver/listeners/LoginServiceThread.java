package com.saad.chatserver.listeners;

import com.saad.chatserver.model.User;
import com.saad.chatserver.ref;
import com.saad.chatserver.service.UserService;

import java.io.*;
import java.net.Socket;

import static com.saad.chatserver.service.Protocols.*;

public class LoginServiceThread extends Thread {

    private Socket socketOfServer;

    private UserService userService;

    private DataInputStream is;

    private DataOutputStream os;

    LoginServiceThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        userService = new UserService();
        System.out.println("New connection with client #" + clientNumber + " at " + socketOfServer);
    }

    @Override
    public void run() {
        try {
            // Open input and output streams
            is = new DataInputStream(socketOfServer.getInputStream());
            os = new DataOutputStream(socketOfServer.getOutputStream());

            while (true) {
                byte protocol = is.readByte();
                // Read data to the server (sent from client).
                System.out.println("Received " + protocol);

                switch (protocol) {
                    case SEARCH_USERNAME_PASSWORD:
                        caseSearchUsernameAndPassword();
                        break;
                    case SEARCH_USERNAME:
                        caseSearchUsername();
                        break;
                    case CREATE_USER:
                        caseCreateUser();
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            try {
                socketOfServer.close();
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void caseCreateUser() throws IOException {
        System.out.println("PROTOCOL: CREATE_USER....");
        final String[] usernameAndPassword = is.readUTF().split(",");
        System.out.println("Creating User with " + usernameAndPassword[0] + " , " + usernameAndPassword[1]);
        final User user = userService.createNewUser(usernameAndPassword[0], usernameAndPassword[1]);
        if (user != null) {
            System.out.println(user.toString());
            os.writeUTF(user.getUsername());
            //LOGIN
            ref.currentUsers.add(user);
            new ChatListener(user).start();
        } else {
            System.out.println("No User");
            os.writeUTF("");
        }
    }

    private void caseSearchUsername() throws IOException {
        System.out.println("PROTOCOL: SEARCH_USERNAME....");
        final String username = is.readUTF();
        System.out.println("Searching for " + username);
        final User user = userService.searchForUsername(username);
        if (user != null) {
            System.out.println(user.toString());
            os.writeUTF(user.getUsername());
        } else {
            System.out.println("No User");
            os.writeUTF("");
        }
    }

    private void caseSearchUsernameAndPassword() throws IOException {
        System.out.println("PROTOCOL: SEARCH_USERNAME_PASSWORD....");
        final String[] usernameAndPassword = is.readUTF().split(",");
        final User user = userService.searchForUsernameAndPassword(usernameAndPassword[0], usernameAndPassword[1]);
        if (user != null) {
            System.out.println(user.toString());
            if (ref.findUser(user)) {
                System.out.println("User already logged...");
                os.writeUTF("");
                os.writeBoolean(true);
            } else {
                os.writeUTF(user.getUsername());
                //LOGIN
                ref.currentUsers.add(user);
                new ChatListener(user).start();
            }
        } else {
            System.out.println("No User");
            os.writeUTF("");
            os.writeBoolean(false);
        }
    }
}
