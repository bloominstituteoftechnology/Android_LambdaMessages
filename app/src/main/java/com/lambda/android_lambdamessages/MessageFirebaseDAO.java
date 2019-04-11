package com.lambda.android_lambdamessages;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


//Part 4 - Handle a Post Request

      // 1. In order to post a message to a message board with this API, you must have the right url, complete a post request, and pass the JSON object to be posted.
   //     The url to post a message consists of the base url, the board id, the messages keyword and then ending the whole thing with .json


// S03M03-5 create class to access firebase database
public class MessageFirebaseDAO {
    private static final String TAG = "JournalFirebaseDAO";

    //2.      The final URL would be https://lambda-message-board.firebaseio.com/THREAD_ID_HERE/messages.json

    private static final String BASE_URL = "https://lambda-message-board.firebaseio.com/THREAD_ID_HERE"  ;
    private static final String ENTRIES_OBJECT = "/messages";

    private static final String URL_ENDING     = ".json";

    private static final String READ_ALL_URL = BASE_URL + ENTRIES_OBJECT + URL_ENDING;
    private static final String CREATE_URL   = BASE_URL + ENTRIES_OBJECT + URL_ENDING;
    private static final String SINGLE_ENTRY = BASE_URL + ENTRIES_OBJECT + "/%s" + URL_ENDING;

    // TODO: CREATE

    public static void createEntry(final Message msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> headerProps = new HashMap<>();
                // 6.   You'll need to replace the hardcoded request type with the type passed in the signature

                headerProps.put("Content-Type", "application/json");

                //  3. To complete your post request, we must adapt your existing httpRequest method to be able to handle POST requests as well as GET requests. Thankfully, there aren't many changes which need to be done to add this functionality
//                First, you'll need to accept two additional parameters in the method signature. a String for the request type and a JSONObect for the request body.
////        It would be best to add String constant values for "POST" and "GET"
                String result = NetworkAdapter.httpRequest(
                        String.format(CREATE_URL, msg.id),
                        NetworkAdapter.POST,
                        msg.toJsonObject(),
                        headerProps);
                try {
                    msg.id=new JSONObject(result).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // TODO: READ ONE
    // TODO: READ ALL
    // S03M03-7 write method to read all entries from database
    public static ArrayList<JournalEntry> readAllEntries() {
        // TODO: Why isn't it grabbing all entries
        final ArrayList<JournalEntry> resultList = new ArrayList<>();

        final String result = NetworkAdapter.httpRequest(READ_ALL_URL);
        try {
            JSONObject topJson = new JSONObject(result);
            for (Iterator<String> it = topJson.keys(); it.hasNext(); ) {
                String key = it.next();
                try{
                    final JSONObject jsonEntry = topJson.getJSONObject(key);
                    String sender = jsonEntry.getString("sender");
                    String text = jsonEntry.getString("text");
                    double timestamp = jsonEntry.getDouble( "timestamp");
                    String id = jsonEntry.getString( "id");
                    String cacheId = null;
                    try {
                        cacheId = jsonEntry.getString("cache_id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String           id        = key;


                    result.add(new Message(

                    ));



                    final JournalEntry entry = new JournalEntry(
                            sender,
                            text,
                            id,
                            timestamp

                    message.setCacheId(cacheId);
                    resultList.add(
                            entry);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.i(TAG, "Finished parsing all entries");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    // TODO: UPDATE
    public static void updateEntry(final JournalEntry entry) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> headerProps = new HashMap<>();
                headerProps.put("Content-Type", "application/json");

                String result = NetworkAdapter.httpRequest(
                        String.format(SINGLE_ENTRY, entry.getFbId()),
                        NetworkAdapter.PUT,
                        entry.toJsonObject(),
                        headerProps);

                // could check result for successful update
            }
        }).start();
    }

    // TODO: DELETE
    public static void deleteEntry(final JournalEntry entry) {
        // TODO: Connect to delete button
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = NetworkAdapter.httpRequest(
                        String.format(SINGLE_ENTRY, entry.getFbId()),
                        NetworkAdapter.DELETE);

                // could check result for successful update
            }
        }).start();
    }
}


