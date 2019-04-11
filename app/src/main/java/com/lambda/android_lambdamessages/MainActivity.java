package com.lambda.android_lambdamessages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        MessageFirebaseDAO mfDAO=new MessageFirebaseDAO();
        ArrayList<Message> ms=mfDAO.readAllEntries();
        TextView tv=findViewById( R.id.text );
        tv.setText( ms.get( 0 ).text);


    }
}
