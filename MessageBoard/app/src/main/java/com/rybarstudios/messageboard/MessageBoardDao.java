package com.rybarstudios.messageboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoardDao {
    public static final String BASE_URL = "https://lambda-message-board.firebaseio.com/";
    public static final String URL_END = ".json";
    public static final String DATABASE_URL = BASE_URL + URL_END;

    public static ArrayList<MessageBoard> getMessageBoards() {
        String result = NetworkAdapter.httpRequest(DATABASE_URL);
        ArrayList<MessageBoard> messageBoards = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.names();
            for(int i = 0; i < jsonArray.length(); i++) {
                String id = jsonArray.getString(i);
                String title = jsonObject.getJSONObject(id).getString("title");
                messageBoards.add(new MessageBoard(title, id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messageBoards;
    }


}
