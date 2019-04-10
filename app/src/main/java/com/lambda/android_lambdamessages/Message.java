package com.lambda.android_lambdamessages;

public class Message {//1 Create a POJO class called Message with the following data members
    String sender, text, id;
    double timestamp;
//2 Create constructors which accept all members, sender and text, and a JSONObject
    public Message(String sender, String text, String id, double timestamp) {//The key's for the JSON object match the names of the variables
        this.sender = sender;
        this.text = text;
        this.id = id; //The default value for id will be null
        this.timestamp = timestamp; //The default value for timestamp will be System.currentTimeMillis() / 1000 which gives us time since the Unix Epoch (January 1970) in seconds
    }
}
