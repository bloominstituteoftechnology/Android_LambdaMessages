package com.example.lambdamessages;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Message implements Parcelable {
    private String sender, text, id;
    private double timestamp;

    public Message(String sender, String text, String id, double timestamp) {
        this.sender = sender;
        this.text = text;
        this.id = id;
        this.timestamp = timestamp;
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

    protected Message(Parcel in) {
        sender = in.readString();
        text = in.readString();
        id = in.readString();
        timestamp = in.readDouble();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sender);
        parcel.writeString(text);
        parcel.writeString(id);
        parcel.writeDouble(timestamp);
    }
}
