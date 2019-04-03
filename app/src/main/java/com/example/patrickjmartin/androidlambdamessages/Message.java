package com.example.patrickjmartin.androidlambdamessages;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Message implements Serializable {

    private String sender, text, id;
    private double timeStamp;

    public Message(JSONObject jsonObject) {
        this.id = null;
        this.timeStamp = System.currentTimeMillis() / 1000;

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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }


    public void setText(String text) {
        this.text = text;
    }

    public String toJsonString() {
        JSONObject json = new JSONObject();

        try {
            json.put("sender", this.sender);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json.put("text", this.text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json.put("timeStamp", this.timeStamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }
}
