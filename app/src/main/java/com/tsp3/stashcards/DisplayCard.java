package com.tsp3.stashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class DisplayCard extends AppCompatActivity {
    private ArrayList<String> fronts=new ArrayList<String>();;
    private ArrayList<String> backs=new ArrayList<String>();;
    private ArrayList<String> currCard=new ArrayList<String>();;
    private JSONArray cards;
    String setID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle extras = getIntent().getExtras();
        setID = (String)extras.get("SetID");
        getData(setID);
       // currCard.add(0,fronts.get(0));

        setContentView(R.layout.activity_display_card);
    }

    protected boolean currSide = false;
    protected int currCardNum = 0;

    protected void flipCard(View view){
        TextView cardtext= (TextView)findViewById(R.id.cardText);
        if(!currSide){
            cardtext.setText(currCard.get(0));
            currSide = true;
        }
        else {
            cardtext.setText(currCard.get(1));
            currSide = false;
        }
    }
    protected void nextCard(View view){
        TextView cardtext = (TextView)findViewById(R.id.cardText);
        if(currCardNum!=fronts.size()-1){
            currCardNum++;
            currCard.add(0,fronts.get(currCardNum));
            currCard.add(1,backs.get(currCardNum));
            currSide=true;
            cardtext.setText(currCard.get(0));
        }

    }
    protected void prevCard(View view){

        TextView cardtext = (TextView)findViewById(R.id.cardText);
        if(currCardNum!=0){
            currCardNum--;
            currCard.add(0,fronts.get(currCardNum));
            currCard.add(1,backs.get(currCardNum));
            currSide=true;
            cardtext.setText(currCard.get(0));
        }


    }
    private String url;
    private void getData(String sid){
        cards = new JSONArray();
         url = "http://tsp3.000webhostapp.com/CardsJsonEdit.php?S_ID="+sid;
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            cards = j.getJSONArray("result");


                            DisplayCard.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    getSets(cards);
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
                fronts.add(json.getString("FrontText"));
                backs.add(json.getString("BackText"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        currCard.add(0,fronts.get(0));
        currCard.add(1,backs.get(0));


    }




}
