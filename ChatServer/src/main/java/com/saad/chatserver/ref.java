package com.saad.chatserver;

import com.saad.chatserver.model.Message;
import com.saad.chatserver.model.User;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ref {

    public static ServerSocket listener = null;

    public static List<User> currentUsers = new ArrayList<>();

    public static List<Message> currentMessages = new ArrayList<>();

    public static List<DataOutputStream> allConnectedClients = new ArrayList<>();

    public static final String ConversationPath = "src/main/resources/db/%s/%s/";

    public static boolean findUser(User user) {
        for (User u : currentUsers){
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public static boolean deleteUser(String username) {
        for (User u : currentUsers){
            if (u.getUsername().equals(username)) {
                currentUsers.remove(u);
                return true;
            }
        }
        return false;
    }
}
