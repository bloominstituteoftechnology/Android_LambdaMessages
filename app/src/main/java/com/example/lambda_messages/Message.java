package com.example.lambda_messages;

import org.json.JSONObject;

public class Message {

    String sender, text, id, key;
    double timestamp;


    public Message(String sender, String text, String id, double timestamp, String key) {
        this.sender = sender;
        this.text = text;
        this.key = key;
        this.id = id;
        this.timestamp = System.currentTimeMillis() / 1000;
    }


}
