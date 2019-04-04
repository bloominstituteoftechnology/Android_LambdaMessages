package com.lambdaschool.android_lambdamessages;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MessageBoardDao {

    private static final String URL_BASE = "https://lambda-message-board.firebaseio.com/";
    private static final String URL_MIDDLE = "-Lb_2nzrahrdW2G38H5u";
    private static final String URL_ENDING = ".json";
    private static final String URL_READ_ALL = URL_BASE + URL_ENDING;
    private static final String URL_READ_SPECIFIC = URL_BASE + URL_MIDDLE + URL_ENDING;

    public ArrayList<MessageBoard> getMessageBoards() {
        ArrayList<MessageBoard> messageBoardArrayList = new ArrayList<>();
        String returnedJsonAsString = NetworkAdapter.httpRequest(URL_READ_ALL);

        try {
            JSONObject fullJson = new JSONObject(returnedJsonAsString);

            for (Iterator<String> it = fullJson.keys(); it.hasNext(); ) {
                String identifier = it.next();

                try {
                    JSONObject jsonJSONObject = fullJson.getJSONObject(identifier);

                    messageBoardArrayList.add(new MessageBoard(jsonJSONObject, identifier));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return messageBoardArrayList;
    }
}
