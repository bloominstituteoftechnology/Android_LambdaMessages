package com.lambdaschool.android_lambdamessages;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoard implements Parcelable {
    private String title, identifier;
    private ArrayList<Message> messages;
    private static Parcel CREATOR;

    public MessageBoard(String title, String identifier) {
        this.title = title;
        this.identifier = identifier;
    }

    public MessageBoard(JSONObject jsonObject, String identifier) {
        getJSONObject("messages").names();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
