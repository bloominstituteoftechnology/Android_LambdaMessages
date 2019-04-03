package com.rybarstudios.messageboard;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoard implements Parcelable {
    private String title, identifier;
    private ArrayList<Message> messages;

    public MessageBoard(JSONObject jsonObject, String identifier) {
        try {
            this.title = jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.identifier = identifier;
    }

    public MessageBoard(String title, String identifier) {
        this.title = title;
        this.identifier = identifier;
    }

    public MessageBoard(Parcel parcel) {
        this.title = parcel.readString();
        this.identifier = parcel.readString();
        Object[] objects = parcel.readArray(Message.class.getClassLoader());
        ArrayList<Message> messageList = new ArrayList<>();
        Message message;
        for (Object object : objects) {
            message = (Message) object;
            messageList.add(message);
        }
        this.messages = messageList;
    }

    public static final Creator<MessageBoard> CREATOR = new Creator<MessageBoard>() {
        @Override
        public MessageBoard createFromParcel(Parcel in) {
            return new MessageBoard(in);
        }

        @Override
        public MessageBoard[] newArray(int size) {
            return new MessageBoard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(identifier);
        dest.writeArray(messages.toArray());
    }

        public String getTitle() {
        return title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
