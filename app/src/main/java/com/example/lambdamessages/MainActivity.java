package com.example.lambdamessages;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "119294";
    ArrayList<MessageBoard> boardList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        context = this;
        populateViews();
    }

    private void populateViews() { // Add all the views for the different threads
        new Thread(new Runnable() {
            @Override
            public void run() {
                boardList = MessageBoardDao.getMessageBoards();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout linearLayout = findViewById(R.id.linear_layout_child);
                        linearLayout.removeAllViews();
                        for (final MessageBoard messageBoard : boardList) {
                            TextView textView = new TextView(context);
                            textView.setText(messageBoard.getTitle());
                            textView.setTextSize(20);
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(context, BoardView.class);
                                    intent.putExtra("MESSAGE_BOARD_KEY",messageBoard);
                                    startActivity(intent);
                                }
                            });
                            textView.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View view) {

                                    Intent intent = new Intent(view.getContext(),SubscriptionMonitorService.class);
                                    intent.putExtra("MESSAGE_BOARD_ID", messageBoard.getIdentifier().toString());
                                    startService(intent);
                                    return true;
                                }
                            });
                            linearLayout.addView(textView);
                        }

                    }
                });
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateViews();
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
