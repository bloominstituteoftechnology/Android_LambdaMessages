package com.example.lambdamessages;

import android.text.Editable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MessageBoardDao {

    private static final String BASE_URL = "https://lambda-message-board.firebaseio.com";
    private static final String END_URL = "/.json";

    public static ArrayList<MessageBoard> getMessageBoards() {
        final ArrayList<MessageBoard> resultList = new ArrayList<>();
        String result = NetworkAdapter.httpRequest(BASE_URL + END_URL);

        try {
            JSONObject jsonTop = new JSONObject(result); //takes the result string and turns it into a big JsonObject
            for (Iterator<String> it = jsonTop.keys(); it.hasNext(); ) { //for each key in the JsonObject

                try {


                    String key = it.next(); //key = the top identifier and increments to the next identifier for next time

                    final JSONObject jsonBoard = jsonTop.getJSONObject(key); //breaking down the boards into specific objects
                    String title = jsonBoard.getString("title"); //grabbing data member
                    resultList.add(new MessageBoard(title, key, jsonBoard)); //passing in data members and the whole board
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

    public static MessageBoard getAMessageBoard(String key) {
        String result = NetworkAdapter.httpRequest(BASE_URL + END_URL);
        try {
            JSONObject jsonTop = new JSONObject(result); //takes the result string and turns it into a big JsonObject
            try {
                final JSONObject jsonBoard = jsonTop.getJSONObject(key); //using our key index to find messageboard
                String title = jsonBoard.getString("title"); //grabbing data member
                return new MessageBoard(title, key, jsonBoard); //passing in data members and the whole board to constructor
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void postMessage(Message message, String boardId) {

        NetworkAdapter.httpRequest(BASE_URL + "/" + boardId + "/messages/" + END_URL, NetworkAdapter.POST, messageToJson(message), null);

    }

    public static void deleteMessage(Message message, String boardId) {
        NetworkAdapter.httpRequest(BASE_URL + "/" + boardId + "/messages/" + message.getId() + END_URL, NetworkAdapter.DELETE, null, null);
    }

    public static JSONObject messageToJson(Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("{\"text\":\"" + message.getText() + "\",")
                .append("\"sender\":\"" + message.getSender() + "\",")
                .append("\"timestamp\":" + message.getTimestamp() + "}");

        try {
            return new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void putMessage(MessageBoard messageBoard, Message oldMessage, String messageText, String senderText) {
        Message newMessage = new Message(senderText, messageText, oldMessage.getId(), oldMessage.getTimestamp());
        NetworkAdapter.httpRequest(BASE_URL + "/" + messageBoard.getIdentifier() + "/messages/" + newMessage.getId() + END_URL, NetworkAdapter.PUT, messageToJson(newMessage), null);
    }
}