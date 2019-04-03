package com.jakeesveld.android_lambdamessages;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MessageBoardsDAO {
    private static final String URL_PREFIX = "https://lambda-message-board.firebaseio.com/";
    private static final String URL_SUFFIX = ".json";
    private static final String GET_ALL_URL = URL_PREFIX + URL_SUFFIX;

    public static ArrayList<MessageBoard> getMessageBoards(){
        String result = NetworkAdapter.httpRequest(GET_ALL_URL);
        ArrayList<MessageBoard> messageBoards = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(result).getJSONObject(MessageBoard.TOP_LEVEL_KEY);
            for (Iterator<String> it = json.keys(); it.hasNext(); ) {
                String key = it.next();
                MessageBoard board = new MessageBoard(json.getJSONObject(key).getString("title"), key);
                messageBoards.add(board);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messageBoards;
    }
}
