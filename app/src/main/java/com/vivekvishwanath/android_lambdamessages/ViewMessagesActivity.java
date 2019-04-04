package com.vivekvishwanath.android_lambdamessages;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewMessagesActivity extends AppCompatActivity {

    ArrayList<Message> messages = new ArrayList<>();
    Context context;
    LinearLayout messageViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);
        context = this;

        messageViewLayout = findViewById(R.id.message_view_layout);

        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageBoard messageBoard = getIntent().getParcelableExtra("Message Board");
                messages = messageBoard.getMessages();
                for (Message message : messages) {
                    final TextView view = new TextView(context);
                    view.setText(message.getText());
                    view.setTextSize(20);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageViewLayout.addView(view);
                        }
                    });
                }
            }
        }).start();
    }
}
