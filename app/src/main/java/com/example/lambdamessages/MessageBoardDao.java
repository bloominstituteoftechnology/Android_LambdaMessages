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
            JSONObject jsonTop = new JSONObject(result); //takes the result string and turns it into a big JsonObject
            for (Iterator<String> it = jsonTop.keys(); it.hasNext(); ) { //for each key in the JsonObject

                try {


                    String key = it.next(); //key = the top identifier and increments to the next identifier for next time

                    final JSONObject jsonBoard = jsonTop.getJSONObject(key); //breaking down the boards into specific objects
                    String title = jsonBoard.getString("title"); //grabbing data member
                    resultList.add(new MessageBoard(title, null, jsonBoard)); //passing in data members and the whole board
                } catch (JSONException e) {
                        e.printStackTrace();
                }

            }
            return resultList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
