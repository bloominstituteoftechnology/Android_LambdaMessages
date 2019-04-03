package com.rybarstudios.messageboard;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private String sender, text, id = null;
    private double timestamp = System.currentTimeMillis() / 1000;

    public Message(String sender, String text, String id, double timestamp) {
        this.sender = sender;
        this.text = text;
        this.id = id;
        this.timestamp = timestamp;
    }

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public Message(JSONObject jsonObject) {
        try {
            this.sender = jsonObject.getString("sender");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.text = jsonObject.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.id = jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.timestamp = jsonObject.getDouble("timestamp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
