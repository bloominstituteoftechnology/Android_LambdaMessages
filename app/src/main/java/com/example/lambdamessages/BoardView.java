package com.example.lambdamessages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BoardView extends AppCompatActivity {
    TextView textViewTitle;
    EditText editTextSender;
    EditText editTextMessage;
    Button sendButton;
    LinearLayout linearLayoutViewGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);
        TextView textViewTitle = findViewById(R.id.board_text_view);
        Intent intent = getIntent();
        final MessageBoard messageBoard = (intent.getParcelableExtra("MESSAGE_BOARD_KEY"));
        textViewTitle.setText(messageBoard.getTitle());


        for (Message message : messageBoard.messages) { // View Generator, TODO:replace with recyclerview
            linearLayoutViewGenerator = findViewById(R.id.linear_layout_child);
            TextView temp = new TextView(this);
            temp.setText(message.getSender() + ": " + message.getText());
            linearLayoutViewGenerator.addView(temp);
        }

        sendButton = findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextMessage = findViewById(R.id.edit_text_message);
                editTextSender = findViewById(R.id.edit_text_sender);
                long timeStamp = (System.currentTimeMillis()/1000);
                final Message message = new Message(
                        editTextSender.getText().toString(),
                        editTextMessage.getText().toString(),
                        null,
                        timeStamp);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        MessageBoardDao.postMessage(message, messageBoard.getIdentifier()); // gettitle is giving me the actual title, not the identifier

                    }
                }).start();
            }
        });

    }
}
