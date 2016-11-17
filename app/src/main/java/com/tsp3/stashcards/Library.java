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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Library extends AppCompatActivity {
    private Spinner spinner;
    public ArrayList<String> libraryContents = new ArrayList<String>();
    public ArrayList<String> sets = new ArrayList<String>();
    public ArrayList<String> creators = new ArrayList<String>();
    private JSONArray result;
    private TextView textViewName;
    private TextView textViewCourse;
    private TextView textViewSession;
    public ArrayAdapter<String> adapter;


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
        getData();
        libraryContents = sets;
        listview = (ListView) findViewById(R.id.list);






        Intent intent = new Intent(this, DisplayCard.class);




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int intPosition = position;
                String clickedValue = listview.getItemAtPosition(intPosition).toString();

                goToCards(Integer.toString(intPosition+1));
            }
        });
    }
    void goToCards(String setinfo){
        Toast.makeText(getApplicationContext(), setinfo, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, DisplayCard.class);
        String message = setinfo;
        intent.putExtra("SetID", message);
        startActivity(intent);
    }

    private void setLibrary(){

    }



   private void getData(){
       result = new JSONArray();
        StringRequest stringRequest = new StringRequest("http://tsp3.000webhostapp.com/SetJson.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("result");


                            Library.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    getSets(result);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void getSets(JSONArray j){


        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                sets.add(json.getString("Set_Name"));
                creators.add(json.getString("creator"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //create adaptor to add array to list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, sets);

        //assign adapter to list view
        listview.setAdapter(adapter);


    }




}
