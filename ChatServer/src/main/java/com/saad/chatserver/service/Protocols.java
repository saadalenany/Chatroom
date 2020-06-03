package com.saad.chatserver.service;

public class Protocols {

    public static final byte SEARCH_USERNAME_PASSWORD = 1;

    public static final byte SEARCH_USERNAME = 2;

    public static final byte CREATE_USER = 3;

    public static final byte NEW_MESSAGE = 4;

    public static final byte REFRESH_CONVERSATION = 5;

    public static final byte REFRESH_USERS = 6;

    public static final byte PUBLIC_MESSAGE = 7;

    public static final byte END_CONVERSATION = 0;

    public static final int SignInOutPort = 7002;

    public static final int ChatPort = 7003;
}
