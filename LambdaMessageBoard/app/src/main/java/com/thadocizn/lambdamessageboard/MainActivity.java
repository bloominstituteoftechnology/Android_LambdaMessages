package com.thadocizn.lambdamessageboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout parent;
    ArrayList<MessageBoard> messageBoards;
    //MessageBoard messageBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageBoards = new ArrayList<>();
        parent = findViewById(R.id.parentLayout);

        new Thread(new Runnable() {
            @Override
            public void run() {
               messageBoards = MessageBoardDao.getMessageBoards();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (MessageBoard messageBoard : messageBoards) {
                            parent.addView(getDefaultTextView(messageBoard));
                        }
                    }
                });
            }
        }).start();
    }

    TextView getDefaultTextView(MessageBoard msgBoard) {
        TextView view = new TextView(this);
        view.setText(msgBoard.getTitle());
        view.setTextSize(24);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
