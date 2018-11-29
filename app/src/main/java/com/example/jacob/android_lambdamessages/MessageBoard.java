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

    public MessageBoard(String title, String identifier, JSONObject json) {
        this.title = title;
        this.identifier = identifier;
        try {
            JSONArray jsonArray = json.getJSONObject("messages").names();
            JSONObject object;
            Message message = null;
            for (int i = 0; i < jsonArray.length(); ++i) {
                object = jsonArray.getJSONObject(i);
                message.setSender(object.getString("sender"));
                message.setText(object.getString("text"));
                message.setTimestamp(object.getDouble("timestamp"));
                message.setId(object.get);
                messages.add(message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeArray(messages.toArray());
    }

    public MessageBoard(Parcel parcel) {
        parcel.readArray(Message.class.getClassLoader());

    }
}
