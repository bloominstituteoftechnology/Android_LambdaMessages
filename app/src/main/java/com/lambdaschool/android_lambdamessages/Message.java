package com.lambdaschool.android_lambdamessages;

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
        this.timestamp = (double) System.currentTimeMillis() / 1000;
    }

    public Message(JSONObject jsonObject) {
        try {
            this.sender = jsonObject.getString("sender");
        } catch (JSONException e) {
            e.printStackTrace();
            this.sender=null;
        }
        try {
            this.text = jsonObject.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
            this.text=null;
        }
        try {
            this.id = jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
            this.id=null;
        }
        try {
            this.timestamp = jsonObject.getDouble("timestamp");
        } catch (JSONException e) {
            e.printStackTrace();
            this.timestamp = (double) System.currentTimeMillis() / 1000;
        }
    }
}
