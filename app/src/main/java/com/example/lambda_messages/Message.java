package com.example.lambda_messages;

import org.json.JSONObject;

public class Message {

    String sender, text, id;
    double timestamp;


    public Message(String sender, String text, String id, JSONObject jsonObject) {
        this.sender = sender;
        this.text = text;
        this.id = id;
        this.timestamp = System.currentTimeMillis() / 1000;
    }


}
