package com.rybarstudios.messageboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBoardDao {
    private static final String BASE_URL = "https://lambda-message-board.firebaseio.com/";
    private static final String URL_END = ".json";
    private static final String DATABASE_URL = BASE_URL + URL_END;
    private static final String MESSAGE = "messages/";
    private static final String IDENTIFIER = "%s/";
    private static final String MESSAGE_URL = BASE_URL + IDENTIFIER + MESSAGE + URL_END;

    public static ArrayList<MessageBoard> getMessageBoards() {
        String result = NetworkAdapter.httpRequest(DATABASE_URL, NetworkAdapter.GET);
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

    public static ArrayList<Message> getMessages(String identifier) {
        ArrayList<Message> messages = new ArrayList<>();
        final String result = NetworkAdapter.httpRequest(String.format(MESSAGE_URL, identifier), NetworkAdapter.GET);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.names();
            for (int i = 0; i < jsonArray.length(); i++) {
                final String id = jsonArray.getString(i);
                JSONObject json = jsonObject.getJSONObject(id);
                messages.add(new Message(json, id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public static void newMessage(String boardId, Message message) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sender", message.getSender());
            jsonObject.put("text", message.getText());
            jsonObject.put("timestamp", message.getTimestamp());
            NetworkAdapter.httpRequest(String.format(MESSAGE_URL, boardId), NetworkAdapter.POST,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
