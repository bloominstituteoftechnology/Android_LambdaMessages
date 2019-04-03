package com.vivekvishwanath.android_lambdamessages;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private String sender, text, id;
    private double timestamp;

    public Message (String sender, String text, String id, double timestamp) {
        this.sender = sender;
        this.text = text;
        this.id = id;
        this.timestamp = timestamp;
    }

    public Message (String sender, String text) {
        this.sender = sender;
        this.text = text;
        this.id = null;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public Message (JSONObject messageJSON) {
        try {
            this.sender = messageJSON.getString("sender");
        } catch (JSONException e) {
            e.printStackTrace();
            this.sender = null;
        }
        try {
            this.text = messageJSON.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
            this.text = null
        }
        try {
            this.id = messageJSON.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
            this.id = null;
        }
        try {
            this.timestamp = messageJSON.getDouble("timestamp");
        } catch (JSONException e) {
            e.printStackTrace();
            this.timestamp = System.currentTimeMillis() / 1000;
        }
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }
}
