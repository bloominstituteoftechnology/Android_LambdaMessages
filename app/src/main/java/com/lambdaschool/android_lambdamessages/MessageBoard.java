package com.lambdaschool.android_lambdamessages;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoard implements Parcelable {
    private String title, identifier;
    private ArrayList<Message> messages;


    public MessageBoard(String title, String identifier) {
        this.title = title;
        this.identifier = identifier;
        this.messages = new ArrayList<>();
    }

    public MessageBoard(JSONObject jsonObject, String identifier) {
        this.messages = new ArrayList<>();
        try {
            this.title=jsonObject.getString("title");
//            this.identifier=??;
            JSONArray jsonArray = jsonObject.getJSONObject("messages").names();
            for (int i = 0; i < jsonArray.length(); i++) {
                this.messages.add(new Message(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public MessageBoard(Parcel in) {
        this.title = in.readString();
        this.identifier = in.readString();
        this.messages = new ArrayList<>();

        Object[] parceledObjects = in.readArray(Message.class.getClassLoader());

        for (Object eachParceled : parceledObjects) {
            this.messages.add((Message) eachParceled);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.identifier);
        dest.writeArray(this.messages.toArray());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MessageBoard createFromParcel(Parcel in) {
            return new MessageBoard(in);
        }

        public MessageBoard[] newArray(int size) {
            return new MessageBoard[size];
        }
    };
}
