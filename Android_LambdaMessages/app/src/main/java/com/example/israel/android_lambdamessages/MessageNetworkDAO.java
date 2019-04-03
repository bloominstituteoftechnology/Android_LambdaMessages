package com.example.israel.android_lambdamessages;

import android.support.annotation.WorkerThread;

import org.json.JSONObject;

public class MessageNetworkDAO {

    public static final String BASE_URL = "https://lambda-message-board.firebaseio.com/";
    public static final String END_URL = "/messages.json";

    @WorkerThread
    public static boolean sendMessage(MessageBoard messageBoard, Message message) {
        return NetworkAdapter.httpRequestPOST(BASE_URL + messageBoard.getIdentifier() + END_URL, message.toJson());
    }
}
