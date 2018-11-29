package com.thadocizn.lambdamessageboard;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Message implements Parcelable {
    String sender, text, id;
    double timestamp;

    public Message(String sender, String text, String id, double timestamp) {
        this.sender = sender;
        this.text = text;
        this.id = null;
        this.timestamp = System.currentTimeMillis();
    }
    public Message(JSONObject jsonObject, String name){
        this.id = name;

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
            this.timestamp = jsonObject.getDouble("timestamp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeString(text);
        dest.writeString(id);
        dest.writeDouble(timestamp);
    }
}
