package com.example.lambda_messages;

import java.util.ArrayList;

public class MessageBoard{

String title, identifier;
ArrayList<Message> messages;

    public MessageBoard(String title, String identifier) {
        this.title = title;
        this.identifier = identifier;
    }
}
