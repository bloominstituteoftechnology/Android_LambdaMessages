package com.example.israel.android_lambdamessages;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MessageBoardsAdapter messageBoardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_message_boards);
        recyclerView.setHasFixedSize(true); // this relates to the width and height. optimization

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        messageBoardsAdapter = new MessageBoardsAdapter();
        recyclerView.setAdapter(messageBoardsAdapter);

        retrieveMessageBoards();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        retrieveMessageBoards();
    }

    private void retrieveMessageBoards() {
        MessageBoardRequestAsyncTask messageBoardRequestAsyncTask = new MessageBoardRequestAsyncTask();
        messageBoardRequestAsyncTask.execute();
    }

    private void updateUi(ArrayList<MessageBoard> messageBoards) {
        messageBoardsAdapter.setMessageBoards(messageBoards);
    }

    private class MessageBoardRequestAsyncTask extends AsyncTask<Void, Void, ArrayList<MessageBoard>> {

        @Override
        protected ArrayList<MessageBoard> doInBackground(Void... voids) {
            return MessageBoardNetworkDAO.getMessageBoards();
        }

        @Override
        protected void onPostExecute(ArrayList<MessageBoard> messageBoards) {
            super.onPostExecute(messageBoards);

            updateUi(messageBoards);
        }
    }
}
