package com.example.lambda_messages;

import android.widget.BaseExpandableListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MessageBoardDao {
    private static String BASE_URL = "https://lambda-message-board.firebaseio.com";
    private static String URL_ENDING = "/.json";
    private static String READ_ALL_MESSAGES = BASE_URL + "/%s" + URL_ENDING;
    private static String READ_ALL_MESSAGE_BOARDS = BASE_URL + URL_ENDING;

    public static ArrayList<MessageBoard> getMessageBoards() {

        final ArrayList<MessageBoard> resultList = new ArrayList<>();

        final String result = NetworkAdapter.httpRequest(READ_ALL_MESSAGE_BOARDS);
        try {
            JSONObject jsonObject = new JSONObject(result);
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();
                try {
                    final JSONObject jsonEntry = jsonObject.getJSONObject(key);
                    String title = jsonEntry.getString("title");
                    String id = key;

                    resultList.add(new MessageBoard(title, id));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }


    public static ArrayList<Message> getMessages(String identifier) {

        final ArrayList<Message> resultList = new ArrayList<>();

        final String result = NetworkAdapter.httpRequest(String.format(READ_ALL_MESSAGES, identifier));
        try {
            JSONObject jsonObject = new JSONObject(result);
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();
                try {
                    JSONArray messages = (JSONArray)jsonObject.get("messages");
                    for(Iterator<JSONObject> jsonKey = messages(); jsonKey.hasNext();) {
                        final JSONObject jsonEntry = jsonKey.next();
                        String sender = jsonEntry.getString("displayName");
                        String text = jsonEntry.getString("text");
                        String senderId = jsonEntry.getString("senderID");
                        double timestamp = jsonEntry.getDouble("timestamp");
                        String keyID = key;

                        resultList.add(new Message(sender, text, senderId, timestamp, keyID));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}


