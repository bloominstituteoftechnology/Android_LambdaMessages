package com.example.jacob.android_lambdamessages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoardDao {
    private static final String USER_ID = "-LQuzp_LDuYHLe3MDLva/";
    private static final String BASE_URL = "https://lambda-message-board.firebaseio.com/";
    private static final String MESSAGES = "messages/";
    private static final String URL_ENDING = ".json";

    private static final String BOARDS_URL = BASE_URL + URL_ENDING;


    public static ArrayList<MessageBoard> getMessageBoards() {
        ArrayList<MessageBoard> boards = new ArrayList<>();
        final String result = NetworkAdapter.httpRequest(BOARDS_URL, NetworkAdapter.GET);

        try {
            JSONObject topLevel = new JSONObject(result);
            JSONArray boardNames =  topLevel.names();
            for(int i = 0; i<boardNames.length();++i) {
                final String id = boardNames.getString(i);
                final String title = topLevel.getJSONObject(id).getString("title");
                boards.add(new MessageBoard(title,id));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return boards;
    }
}
