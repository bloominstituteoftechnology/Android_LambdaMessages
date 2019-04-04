package com.example.lambdamessages;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageBoard implements Parcelable {
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
    private String title, identifier;
    ArrayList<Message> messages = new ArrayList<>();


    public MessageBoard(String title, String identifier, JSONObject jsonObject) {
        this.title = title;
        this.identifier = identifier;
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONObject("messages").names();


            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Object name = jsonArray.get(i); // iterate through all messages and add them to an arraylist of messaages
                    JSONObject message = jsonObject.getJSONObject("messages").getJSONObject(name.toString());
                    this.messages.add(new Message(
                            message.getString("sender"),
                            message.getString("text"),
                            name.toString(),
                            message.getDouble("timestamp")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected MessageBoard(Parcel in) {
        title = in.readString();
        identifier = in.readString();
        Object[] parceledObjects = in.readArray(Message.class.getClassLoader());
        for (Object eachParceled : parceledObjects) {
            messages.add((Message) eachParceled);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setMessages(JSONObject jsonObject) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(identifier);
        parcel.writeArray(messages.toArray());
    }


}
