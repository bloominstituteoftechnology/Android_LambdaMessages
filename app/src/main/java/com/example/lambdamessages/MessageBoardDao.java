package com.example.lambdamessages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MessageBoardDao {

    private static final String BASE_URL = "https://lambda-message-board.firebaseio.com";
    private static final String END_URL = "/.json";

    public static ArrayList<MessageBoard> getMessageBoards(){
        final ArrayList<MessageBoard> resultList = new ArrayList<>();
        String result = NetworkAdapter.httpRequest(BASE_URL + END_URL);

        try {
            JSONObject jsonTop = new JSONObject(result);
            for (Iterator<String> it = jsonTop.keys(); it.hasNext(); ) {
                String key = it.next();

                final JSONObject jsonBoard = jsonTop.getJSONObject(key);
                String identifier = jsonBoard.getString("identifier");
                String title = key;
                resultList.add(new MessageBoard(title,identifier));
            }
            return resultList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
