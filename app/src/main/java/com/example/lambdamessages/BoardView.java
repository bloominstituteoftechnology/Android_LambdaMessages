package com.example.lambdamessages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BoardView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);

        TextView textView = findViewById(R.id.board_text_view);
        Intent intent = getIntent();
        MessageBoard messageBoard = (intent.getParcelableExtra("MESSAGE_BOARD_KEY"));
    }
}
