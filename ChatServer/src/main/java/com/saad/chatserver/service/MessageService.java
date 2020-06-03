package com.saad.chatserver.service;

import com.opencsv.CSVWriter;
import com.saad.chatserver.model.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class MessageService {

    public Message saveMessage(String conversationFile, Message message) {
        try {
            FileWriter fileWriter = new FileWriter(conversationFile, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            message.setDate(LocalDateTime.now());
            final String filtered = message.getContent().replaceAll("\"", "").replaceAll(",", " ");
            csvWriter.writeNext(new String[]{message.getUsername(), filtered, message.getDate().toString()});
            csvWriter.flushQuietly();
            //CLOSE
            csvWriter.close();
            fileWriter.close();
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
