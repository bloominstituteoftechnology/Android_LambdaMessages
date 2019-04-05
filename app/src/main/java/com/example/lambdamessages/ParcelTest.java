package com.example.lambdamessages;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ParcelTest implements Parcelable {
    private ArrayList<MessageBoard> messageBoards;
    private String   a, test, is, underway;
    private int      please, dont, disturb;


    //Test successful, once datamembers were declared, all parcel methods were auto-generated

    private ParcelTest(Parcel in) {
        messageBoards = in.createTypedArrayList(MessageBoard.CREATOR);
        a = in.readString();
        test = in.readString();
        is = in.readString();
        underway = in.readString();
        please = in.readInt();
        dont = in.readInt();
        disturb = in.readInt();
    }

    public static final Creator<ParcelTest> CREATOR = new Creator<ParcelTest>() {
        @Override
        public ParcelTest createFromParcel(Parcel in) {
            return new ParcelTest(in);
        }

        @Override
        public ParcelTest[] newArray(int size) {
            return new ParcelTest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(messageBoards);
        parcel.writeString(a);
        parcel.writeString(test);
        parcel.writeString(is);
        parcel.writeString(underway);
        parcel.writeInt(please);
        parcel.writeInt(dont);
        parcel.writeInt(disturb);
    }
}
