package com.example.jacob.android_lambdamessages;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int LAYOUT_SPAN_COUNT = 2;

    private Context context;
    private Activity activity;
    private GridLayoutManager layoutManager;
    private RecyclerView listView;
    private MessageBoardListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        activity = this;

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new offloadTask().execute();
            }
        });
        listView = findViewById(R.id.messageboard_recycler_view);
        layoutManager = new GridLayoutManager(context, LAYOUT_SPAN_COUNT);
        listView.setLayoutManager(layoutManager);
        new offloadTask().execute();
    }



    public class offloadTask extends AsyncTask<Void, Integer, ArrayList<MessageBoard>> {

/*        @Override
        protected void onPostExecute(ArrayList<MessageBoard> boards) {
            if (boards != null) {
                MessageBoardListAdapter adapter = new MessageBoardListAdapter(boards);
                listView.setAdapter(adapter);
            }
        }*/

        @Override
        protected void onPostExecute(ArrayList<MessageBoard> messageBoards) {
            super.onPostExecute(messageBoards);
            if (messageBoards != null) {
                listAdapter = new MessageBoardListAdapter(messageBoards);
                listView.setAdapter(listAdapter);
            }
        }

        @Override
        protected ArrayList<MessageBoard> doInBackground(Void... voids) {
            final ArrayList<MessageBoard> messageBoards = MessageBoardDao.getMessageBoards();
            return messageBoards;
        }
    }





}
