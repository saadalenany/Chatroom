package com.saad.chatserver.model;

import com.opencsv.bean.CsvBindByName;

public class User {

    @CsvBindByName(column = "username", required = true)
    private String username;

    @CsvBindByName(column = "password", required = true)
    private String password;

    @CsvBindByName(column = "noc")
    private Integer noc;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNoc() {
        return noc;
    }

    public void setNoc(Integer noc) {
        this.noc = noc;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", noc='" + noc + '\'' +
                '}';
    }
}
