package com.example.lambdamessages;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

public class SubscriptionCacheDao {
    private static final String SUB_PREFS = "SubscriptionPreferences";

    private static final String ID_LIST_KEY = "id_list";
    private static SharedPreferences prefs;

    public SubscriptionCacheDao(Context context) {
        prefs = context.getSharedPreferences(SUB_PREFS, Context.MODE_PRIVATE);
    }

    public ArrayList<String> getAllSubscriptions(){ //Not fully implemented
        String temp = prefs.getString(ID_LIST_KEY, "");
        String[] subs = temp.split(",");
        return new ArrayList<>(Arrays.asList(subs));
    }

    public static boolean checkSubscription(String identifier) {
        return prefs.contains(identifier);
    }

    public void unSubscribe(String identifier) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(identifier);
        editor.apply();
    }

    public void subscribe(String identifier) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(identifier, ""); //empty string since being on the list means you are subscribed
        editor.apply();
    }

}
