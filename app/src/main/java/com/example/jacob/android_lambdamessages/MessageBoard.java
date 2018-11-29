package com.example.jacob.android_lambdamessages;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoard implements Parcelable {
    String title, identifier;
    ArrayList<Message> messages;
    JSONObject json;

    public MessageBoard(String title, String identifier) {
        this.title = title;
        this.identifier = identifier;
/*        try {
            JSONArray jsonArray = json.getJSONObject("messages").names();
            JSONObject object;

            for (int i = 0; i < jsonArray.length(); ++i) {
                object = jsonArray.getJSONObject(i);
                this.json = object;
//TODO  DO I need something different here?.
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
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

    public String getTitle() {
        return title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeArray(messages.toArray());
    }

    public MessageBoard(Parcel parcel) {
        Object[] objects = parcel.readArray(Message.class.getClassLoader());
        messages = new ArrayList<>();
        Message message = null;
        for (Object object : objects) {
            message = (Message) object;
            messages.add(message);
        }
    }
}
