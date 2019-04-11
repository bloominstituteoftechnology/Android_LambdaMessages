package com.lambda.android_lambdamessages;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.JulianFields;

public class Message {//1 Create a POJO class called Message with the following data members
    String sender, text, Id, cacheId;
    String id;
    double timestamp;

    //2 Create constructors which accept all members, sender and text, and a JSONObject
    public Message(String sender, String text, String id, double timestamp) {//The key's for the JSON object match the names of the variables
        this.sender = sender;
        this.text = text;
        this.id = id; //The default value for id will be null
        this.timestamp = timestamp; //The default value for timestamp will be System.currentTimeMillis() / 1000 which gives us time since the Unix Epoch (January 1970) in seconds
    }

    public Message() {
        this.sender = "";
        this.text = "";
        this.id = ""; //The default value for id will be null
        initializeDate();
    }

    private void initializeDate() {
        timestamp = (double) (System.currentTimeMillis() / 1000);
    }

    // write method to convert entry into jsonObject
    public JSONObject toJsonObject() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put( "sender", sender );
            jsonObject.put( "text", text );
            jsonObject.put( "id", id );
            jsonObject.put( "timestamp", timestamp );
            jsonObject.put( "cache_id", cacheId );
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                return new JSONObject( String.format( "{\"date\":\"%s\",\"text\":\"%s\",\"id\":\"%s\",\"timestamp\":%d,\"cache_id\":\"%s\"}",
                        sender, text, id, timestamp, cacheId ) );
            } catch (JSONException e1) {
                e1.printStackTrace();
                return null;
            }
        }


    }
}