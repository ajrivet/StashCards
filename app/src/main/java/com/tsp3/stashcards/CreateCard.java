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
import android.widget.Button;
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

import static android.R.attr.onClick;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.widget.Toast.LENGTH_SHORT;


public class CreateCard extends AppCompatActivity{
    public ArrayList<String> libraryContents = new ArrayList<String>();
    public ArrayList<String> sets = new ArrayList<String>();
    private JSONArray result;
   // private Spinner spinner;
    private Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addListenerButton();
    }


    public void addListenerButton() {
        // spinner = (Spinner) findViewById(R.id.spinner1);
        submit = (Button) findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                submit(v);
            }

        });
    }



/**
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                System.out.println("WOAH");

                Toast.makeText(parent.getContext(), "Selected: " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                System.out.println("Really?");
            }
        });
    }
**/
/**    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        System.out.println("WOW");
        TextView myText = (TextView) view;
        Toast.makeText(this, "You selected " + myText.getText(), Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> adapterView){
        System.out.println("FAIL");

    }
**/
/**
    public void addSetsToSpinner(){
        getData();
        spinner = (Spinner) findViewById(R.id.spinner1);

        libraryContents = sets;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    public void addListenerToSpinner(){
  //      spinner.setOnItemSelectedListener(new BetterOnItemSelectedListener());

    }

 **/










    public void submit(View view){
        EditText editText = (EditText) findViewById(R.id.front_text);
        String frontText = editText.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.back_text);
        String backText = editText2.getText().toString();
        EditText editText3 = (EditText) findViewById(R.id.set_number);
        String setNumber = editText3.getText().toString();
        System.out.println(frontText+" "+backText+" "+setNumber);


        sendData(frontText, backText, setNumber);
        System.out.println("TestBOI");













        Intent intent = new Intent(this, Library.class);






    }
    private String url;
    private void sendData(String frontText, String backText, String setNumber){
        url = "http://tsp3.000webhostapp.com/AddCard.php?S_ID="+setNumber+"&FRONT="+frontText+"&BACK="+backText;
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



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



