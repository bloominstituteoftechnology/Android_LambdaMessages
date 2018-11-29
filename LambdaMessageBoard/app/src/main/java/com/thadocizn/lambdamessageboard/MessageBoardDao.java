package com.thadocizn.lambdamessageboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoardDao {
    private static final String BASE_URL = "https://lambda-message-board.firebaseio.com/";
    private static final String END_URL = ".JSON";
    private static final String MESSAGES = "messages/";
    public static final String URL = BASE_URL + END_URL;

    public static ArrayList<MessageBoard> getMessageBoards() {
        ArrayList<MessageBoard> messageBoards = new ArrayList<>();

        String result = NetworkAdapter.httpRequest(URL, NetworkAdapter.GET);
        try {
            JSONObject topLevel = new JSONObject(result);
            JSONArray messageBoardIds = topLevel.names();
            for (int i = 0; i < messageBoardIds.length(); i++) {
                messageBoards.add(new MessageBoard(topLevel.getJSONObject(messageBoardIds.getString(i)), messageBoardIds.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messageBoards;
    }
}