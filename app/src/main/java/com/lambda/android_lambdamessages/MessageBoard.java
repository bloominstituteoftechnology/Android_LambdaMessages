package com.lambda.android_lambdamessages;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
//3. Create another POJO class called MessageBoard with the following data members

public class MessageBoard implements Parcelable {
    String title, identifier;
    ArrayList<Message> messages;

    public MessageBoard(String title, String identifier) {
        this.title = title;
        this.identifier = identifier;
    //    this.messages = messages;
    }
    public void writeToParcel(Parcel parcel ,int flags){

        parcel.writeParcelable( this,1);

    }
}
