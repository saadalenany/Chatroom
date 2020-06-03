package com.saad.chatserver.model;

import com.opencsv.bean.CsvBindByName;

import java.time.LocalDateTime;

public class Message {

    @CsvBindByName(column = "username", required = true)
    private String username;

    @CsvBindByName(column = "content")
    private String content;

    @CsvBindByName(column = "date")
    private LocalDateTime date;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
