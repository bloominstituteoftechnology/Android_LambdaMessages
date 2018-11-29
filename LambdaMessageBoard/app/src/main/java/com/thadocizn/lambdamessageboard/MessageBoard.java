package com.thadocizn.lambdamessageboard;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoard implements Parcelable {
    String title, identifier;
    ArrayList<Message> messages;
    String result = NetworkAdapter.httpRequest(String.format(MessageBoardDao.URL, identifier), NetworkAdapter.GET);

    public MessageBoard(String title, String identifier) {
        this.title = title;
        this.identifier = identifier;
    }
    public MessageBoard(JSONObject jsonObject, String identifier){
        this.identifier = identifier;

        try {
            this.title = jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject topLevel = new JSONObject(result);
            JSONArray messageIds = topLevel.names();
            for (int i = 0; i < messageIds.length(); i++) {
                String strMessage = messageIds.getString(i);
                messages.add(new Message(topLevel.getJSONObject(strMessage), strMessage));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected MessageBoard(Parcel in) {
        title = in.readString();
        identifier = in.readString();
        result = in.readString();
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(identifier);
        dest.writeArray(messages.toArray());

    }
}
