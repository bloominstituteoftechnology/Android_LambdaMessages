package com.vivekvishwanath.android_lambdamessages;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout messageBoardsLayout;
    ArrayList<MessageBoard> messageBoards;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        messageBoardsLayout=findViewById(R.id.message_boards_layout);

        new Thread(new Runnable() {
            @Override
            public void run() {
                messageBoards = MessageBoardDao.getMessageBoards();
                for (MessageBoard messageBoard : messageBoards) {
                    final TextView view = new TextView(context);
                    view.setText(messageBoard.getTitle());
                    view.setTextSize(20);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBoardsLayout.addView(view);
                        }
                    });
                }
            }
        }).start();
    }
}
