package com.example.lambdamessages;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BoardView extends AppCompatActivity {
    TextView textViewTitle;
    EditText editTextSender;
    EditText editTextMessage;
    Button sendButton;
    Button editButton;
    LinearLayout linearLayoutViewGenerator;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);
        context = this;
        TextView textViewTitle = findViewById(R.id.board_text_view);
        Intent intent = getIntent();
        final MessageBoard messageBoard = (intent.getParcelableExtra("MESSAGE_BOARD_KEY"));
        textViewTitle.setText(messageBoard.getTitle());
        populateBoard(messageBoard);
        editButton = findViewById(R.id.button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        sendButton = findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextMessage = findViewById(R.id.edit_text_message);
                editTextSender = findViewById(R.id.edit_text_sender);
                long timeStamp = (System.currentTimeMillis() / 1000);
                final Message message = new Message(
                        editTextSender.getText().toString(),
                        editTextMessage.getText().toString(),
                        null,
                        timeStamp);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MessageBoardDao.postMessage(message, messageBoard.getIdentifier());
                        final MessageBoard newBoard = MessageBoardDao.getAMessageBoard(messageBoard.getIdentifier()); //new messageboard GOT from server
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateBoard(newBoard); //redo the views
                                editTextMessage.setText("");
                                editTextSender.setText("");
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void populateBoard(final MessageBoard messageBoard) {
        linearLayoutViewGenerator = findViewById(R.id.linear_layout_child);
        linearLayoutViewGenerator.removeAllViews();
        for (final Message message : messageBoard.messages) { // View Generator, TODO:replace with recyclerview
            final TextView textView = new TextView(this);
            textView.setText(message.getSender() + ": " + message.getText());
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popup = new PopupMenu(context, textView);
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete_menu_button:
                                    Log.i("appLog", "Delete triggered");
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MessageBoardDao.deleteMessage(message, messageBoard.getIdentifier());
                                            final MessageBoard newBoard = MessageBoardDao.getAMessageBoard(messageBoard.getIdentifier()); //new messageboard GOT from server
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    populateBoard(newBoard); //redo the views
                                                }
                                            });
                                        }
                                    }).start();
                                    return true;
                                case R.id.edit_menu_button:
                                    editTextMessage = findViewById(R.id.edit_text_message);
                                    editTextSender = findViewById(R.id.edit_text_sender);
                                    sendButton.setVisibility(View.GONE);
                                    editButton.setVisibility(View.VISIBLE);
                                    editTextSender.setText(message.getSender());
                                    editTextMessage.setText(message.getText());

                                    editButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    MessageBoardDao.putMessage(messageBoard, message, editTextMessage.getText().toString(), editTextSender.getText().toString());
                                                    Log.i("appLog", "Edit triggered");
                                                    final MessageBoard newBoard = MessageBoardDao.getAMessageBoard(messageBoard.getIdentifier()); //new messageboard GOT from server
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            populateBoard(newBoard); //redo the views
                                                            editTextMessage.setText("");
                                                            editTextSender.setText("");
                                                            editButton.setVisibility(View.GONE);
                                                            sendButton.setVisibility(View.VISIBLE);
                                                        }
                                                    });
                                                }
                                            }).start();
                                        }
                                    });
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();
                    return true;
                }
            });
            linearLayoutViewGenerator.addView(textView);
        }
    }
}
