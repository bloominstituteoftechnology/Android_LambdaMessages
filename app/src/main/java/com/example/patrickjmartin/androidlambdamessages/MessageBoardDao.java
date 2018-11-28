package com.example.patrickjmartin.androidlambdamessages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoardDao {

    public static final String BASE_URL = "https://lambda-message-board.firebaseio.com/";
    public static final String URL_END = ".json";

    public static ArrayList<MessageBoard> getMessageBoards(){
        ArrayList<MessageBoard> mbs = new ArrayList<>();
        final String result = NetworkAdapter.httpRequest(BASE_URL + URL_END, NetworkAdapter.GET);

        try {
            JSONObject topLevel = new JSONObject(result);
            JSONArray mbIDS = topLevel.names();
            for (int i = 0; i < mbIDS.length(); i++) {
                final String ids = mbIDS.getString(i);
                mbs.add(new MessageBoard(topLevel.getJSONObject(ids), ids));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mbs;
    }

}
