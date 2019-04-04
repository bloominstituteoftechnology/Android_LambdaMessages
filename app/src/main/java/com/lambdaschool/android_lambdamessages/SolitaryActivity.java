package com.lambdaschool.android_lambdamessages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class SolitaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solitary);

        ArrayList<Message> messageArrayList = getIntent().getParcelableArrayListExtra("messageboard");

        LinearLayout linearLayout = findViewById(R.id.linear_layout_solitary);

        for (Message eachM : messageArrayList) {
            TextView textView = new TextView(getApplicationContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setPadding(5, 30, 5, 0);
            String formattedMessageBoard = "Sender: %s | Time: %d | id: %s | Text: %s";
            textView.setText(String.format(Locale.US, formattedMessageBoard, eachM.getSender(), (int) eachM.getTimestamp(), eachM.getId(), eachM.getText()));
            linearLayout.addView(textView);
        }
    }
}
