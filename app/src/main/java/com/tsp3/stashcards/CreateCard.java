package com.tsp3.stashcards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.tsp3.stashcards.R.id.spinner1;

public class CreateCard extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public ArrayList<String> libraryContents = new ArrayList<String>();
    public ArrayList<String> sets = new ArrayList<String>();
    public ArrayList<String> creators = new ArrayList<String>();
    private JSONArray result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getData();
        System.out.println("Test");
        String id = "";
        libraryContents = sets;
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);


    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        TextView myText = (TextView) view;
        Toast.makeText(this, "You selected " + myText.getText(), Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> adapterView){
        System.out.println("FAIL");

    }





       /**- spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Toast.makeText(parent.getContext(), "Selected: " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                System.out.println("Really?");
            }
        });
    }
**/

    public void submit(){
        EditText editText = (EditText) findViewById(R.id.front_text);
        String frontText = editText.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.back_text);
        String backText = editText.getText().toString();


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


                            CreateCard.this.runOnUiThread(new Runnable() {
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
       // listview.setAdapter(adapter);


    }

}



