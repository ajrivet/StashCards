package com.tsp3.stashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class DisplayCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_card);
    }

    protected boolean currSide = false;

    protected void flipCard(View view){
        TextView cardtext= (TextView)findViewById(R.id.cardText);
        if(!currSide){
            cardtext.setText("This is the front Side");
            currSide = true;
        }
        else {
            cardtext.setText("This is the Back Side");
            currSide = false;
        }
    }




}
