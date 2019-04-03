package com.vivekvishwanath.android_lambdamessages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoardDao {

    private static final String BASE_URL = "https://lambda-message-board.firebaseio.com/";
    private static final String URL_ENDING = ".json";

    public static ArrayList<MessageBoard> getMessageBoards() {
        ArrayList<MessageBoard> messageBoards = new ArrayList<>();
        String result = NetworkAdapter.httpGETRequest(BASE_URL + URL_ENDING);

        try {
            // topLevel unwraps first curl
            JSONObject topLevel = new JSONObject(result);
            JSONArray messageBoardKeys = topLevel.names();
            for (int i = 0; i < messageBoardKeys.length(); i++) {
                JSONObject messageBoardJSONObject = topLevel.getJSONObject(messageBoardKeys.getString(i));
                messageBoards.add(new MessageBoard(messageBoardJSONObject, messageBoardKeys.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messageBoards;
    }
}
