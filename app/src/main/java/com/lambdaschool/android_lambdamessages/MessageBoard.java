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
                //JSONObject message = jsonObject.getJSONObject("messages").getJSONObject(name.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public MessageBoard(Parcel in) {
        JSONObject jsonObject=;
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONObject("messages").names();

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Object name = jsonArray.get(i);
                    JSONObject message = jsonObject.getJSONObject("messages").getJSONObject(name.toString());
                    this.messages.add(new Message(
                            message.getString("sender"),
                            message.getString("text"),
                            null,
                            message.getDouble("timestamp")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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
        dest.writeString(this.title);
        dest.writeString(this.identifier);
        dest.writeArray(this.messages.toArray());
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
}
