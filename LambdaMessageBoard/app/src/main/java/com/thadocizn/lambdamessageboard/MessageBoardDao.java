package com.thadocizn.lambdamessageboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoardDao {
    private static final String BASE_URL = "https://lambda-message-board.firebaseio.com/";
    private static final String END_URL = ".json";
    private static final String MESSAGES = "messages/";
    private static final String THREAD_ID = "%s";
    public static final String URL = BASE_URL + END_URL;
    private static final String FINAL_URL = BASE_URL + THREAD_ID + MESSAGES +END_URL;
    private static final String TEXT = "text";
    private static final String SENDER = "sender";
    private static final String ID = "id";
    private static final String TIMESTAMP = "timestamp";
    public static final String POST = "POST";

    public static ArrayList<MessageBoard> getMessageBoards() {
        ArrayList<MessageBoard> messageBoards = new ArrayList<>();

        String result = NetworkAdapter.httpRequest(URL, NetworkAdapter.GET);
        try {
            JSONObject topLevel = new JSONObject(result);
            JSONArray messageBoardIds = topLevel.names();
            for (int i = 0; i < messageBoardIds.length(); i++) {
                String strMessageId = messageBoardIds.getString(i);
                messageBoards.add(new MessageBoard(topLevel.getJSONObject(strMessageId), strMessageId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messageBoards;
    }

    public static void postMessage(String id, Message message){
        JSONObject jsonObject = null;
        String finalUrl = FINAL_URL.replace(THREAD_ID, id);
        String result = NetworkAdapter.httpRequest(finalUrl, POST, jsonObject.toString());


        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            jsonObject.put(SENDER, message.getSender());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObject.put(TEXT, message.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObject.put(ID, message.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObject.put(TIMESTAMP, message.getTimestamp());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}