package com.vivekvishwanath.android_lambdamessages;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoard implements Parcelable {

    private String title, identifer;
    private ArrayList<Message> messages;

    public MessageBoard(String title, String identifer) {
        this.title = title;
        this.identifer = identifer;
    }

    public MessageBoard(JSONObject messageBoardJSON, String identifier) {
        try {
            JSONArray messageJSONArray = messageBoardJSON.getJSONObject("messages").names();
            for (int i = 0; i < messageJSONArray.length(); i++) {
                JSONObject messageJSONObject = messageJSONArray.getJSONObject(i);
                messages.add(new Message(messageJSONObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public MessageBoard(Parcel parcel) {
        this.title = parcel.readString();
        this.identifer = parcel.readString();
        Object[] parcelArray = parcel.readArray(Message.class.getClassLoader());
        messages = new ArrayList<>();
        for (int i = 0; i < parcelArray.length; i++) {
            messages.add((Message) parcelArray[i]);
        }
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

    public String getIdentifer() {
        return identifer;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.identifer);
        dest.writeArray(this.messages.toArray());
    }
}
