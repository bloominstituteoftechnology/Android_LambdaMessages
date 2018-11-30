package com.example.jacob.android_lambdamessages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageViewActivity extends AppCompatActivity {

    public static final String VIEW_BOARD_KEY = "view board key";

    MessageBoard inputBoard;
    Context context;
    private Activity activity;
    private LinearLayoutManager layoutManager;
    private RecyclerView listView;
    private MessageListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        context = this;
        activity = this;

        inputBoard = (MessageBoard) getIntent().getParcelableExtra(VIEW_BOARD_KEY);
        final String title = inputBoard.getTitle();
        ((TextView) findViewById(R.id.text_message_title)).setText(title);

        listView = findViewById(R.id.message_recycler_view);
        layoutManager = new LinearLayoutManager(context);
        listView.setLayoutManager(layoutManager);
        ArrayList<Message> dummy = new ArrayList<>();
        listAdapter = new MessageListAdapter(dummy, activity);
        listView.setAdapter(listAdapter);
        new offloadTask().execute(inputBoard.getIdentifier());
    }

    public class offloadTask extends AsyncTask<String, Integer, ArrayList<Message>> {

        @Override
        protected void onPostExecute(ArrayList<Message> messages) {
            super.onPostExecute(messages);
            if (messages != null) {
                listAdapter = new MessageListAdapter(messages, activity);
                listView.setAdapter(listAdapter);
            }
        }

        @Override
        protected ArrayList<Message> doInBackground(String... strings) {
            final ArrayList<Message> messageList = MessageBoardDao.getMessages(strings[0]);
            return messageList;
        }
    }

}

