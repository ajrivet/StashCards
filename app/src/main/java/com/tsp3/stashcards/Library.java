package com.tsp3.stashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Library extends AppCompatActivity {


    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
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
        listview = (ListView) findViewById(R.id.list);
        //create array to hold all the set names
        final String[] librarycontents = new String[]{
                "SampleSet1", "SampleSet2"
        };
        //create adaptor to add array to list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, librarycontents);

        //assign adapter to list view
        listview.setAdapter(adapter);
        Intent intent = new Intent(this, DisplayCard.class);




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int intPosition = position;
                String clickedValue = listview.getItemAtPosition(intPosition).toString();

                goToCards(clickedValue);
            }
        });
    }
    void goToCards(String setinfo){
        Toast.makeText(getApplicationContext(), setinfo, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, DisplayCard.class);
        String message = setinfo;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}
