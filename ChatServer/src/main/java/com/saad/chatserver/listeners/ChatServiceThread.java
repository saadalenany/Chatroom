package com.saad.chatserver.listeners;

import com.opencsv.exceptions.CsvException;
import com.saad.chatserver.model.Message;
import com.saad.chatserver.model.User;
import com.saad.chatserver.ref;
import com.saad.chatserver.service.CsvFiles;
import com.saad.chatserver.service.MessageService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.saad.chatserver.service.Protocols.*;

class ChatServiceThread extends Thread {

    private Socket socketOfServer;

    private MessageService messageService;

    private DataInputStream is;

    private User user;

    private boolean infinite = true;

    ChatServiceThread(Socket socketOfServer, User user) {
        this.socketOfServer = socketOfServer;
        this.user = user;
        this.messageService = new MessageService();
    }

    @Override
    public void run() {
        DataOutputStream os = null;
        try {
            // Open input and output streams
            is = new DataInputStream(socketOfServer.getInputStream());
            os = new DataOutputStream(socketOfServer.getOutputStream());
            ref.allConnectedClients.add(os);

            //DEPLOY CURRENT CHAT CONVERSATION
            final String conversationFolder = CsvFiles.prepareConversationFile(user);

            while (infinite) {
                byte protocol = is.readByte();
                // Read data to the server (sent from client).
                System.out.println("Received " + protocol);

                switch (protocol) {
                    case NEW_MESSAGE:
                        saveAndUpdateToAll(conversationFolder);
                        break;
                    case REFRESH_CONVERSATION:
                        refreshConversations();
                        break;
                    case REFRESH_USERS:
                        refreshUsers();
                        break;
                    case END_CONVERSATION:
                        //LOGOUT USER
                        ref.currentUsers.remove(user);
                        is.close();
                        os.close();
                        ref.allConnectedClients.remove(os);
                        socketOfServer.close();
                        //END CONVERSATION FILE
                        //DUMP STATS FILE
                        CsvFiles.dumpStatsFile(conversationFolder, "conversation.csv");
                        //DEPLOY CURRENT USERS NAMES
                        refreshUsers();
                        infinite = false;
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
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

    private void refreshConversations() throws IOException {
        for (DataOutputStream os: ref.allConnectedClients) {
            os.writeByte(REFRESH_CONVERSATION);
            os.writeInt(ref.currentMessages.size());
            for (Message m : ref.currentMessages) {
                os.writeUTF(m.getUsername());
                os.writeUTF(m.getContent());
                os.writeUTF(m.getDate().toString().replaceAll("T", " "));
            }
        }
    }

    private void saveAndUpdateToAll(String conversationFolder) throws IOException {
        //SAVE THE MESSAGE
        Message message = new Message();
        message.setUsername(user.getUsername());
        message.setContent(is.readUTF());
        String messageDate = is.readUTF();
        ref.currentMessages.add(messageService.saveMessage(conversationFolder.concat("conversation.csv"), message));

        //SEND AN UPDATE TO ALL
        for (DataOutputStream os: ref.allConnectedClients) {
            os.writeByte(PUBLIC_MESSAGE);
            os.writeUTF(message.getUsername());
            os.writeUTF(message.getContent());
            os.writeUTF(messageDate);
        }
    }

    private void refreshUsers() throws IOException {
        for (DataOutputStream os: ref.allConnectedClients) {
            os.writeByte(REFRESH_USERS);
            System.out.println("Users " + ref.currentUsers.size() + " | " + ref.currentUsers);
            os.writeInt(ref.currentUsers.size());
            for (User user : ref.currentUsers) {
                os.writeUTF(user.getUsername());
            }
        }
    }
}
