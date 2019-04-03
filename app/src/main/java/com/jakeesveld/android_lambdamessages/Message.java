package com.jakeesveld.android_lambdamessages;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private String sender, text, id;
    private double timestamp;

    public Message(String sender, String text, String id, double timestamp) {
        this.sender = sender;
        this.text = text;
        this.id = id;
        this.timestamp = timestamp;
    }

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
        this.id = null;
        this.timestamp = System.currentTimeMillis();
    }

    public Message(JSONObject json){
        try {
            this.sender = json.getString("sender");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.text = json.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.id = json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.timestamp = json.getDouble("timestamp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public double getTimestamp() {
        return timestamp;
    }
}
