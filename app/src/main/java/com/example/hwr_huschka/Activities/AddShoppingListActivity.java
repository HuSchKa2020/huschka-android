package com.example.hwr_huschka.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hwr_huschka.Constants;
import com.example.hwr_huschka.DatabaseHelper;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddShoppingListActivity extends AppCompatActivity {

    Button btnAddList;
    DatePicker datePicker;
    Spinner spinner;
    Toolbar toolbar;

    SharedPreferences sharedPreferences;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);

        // get UserID from SharedPreferences
        sharedPreferences = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt("id", 0);

        btnAddList = findViewById(R.id.btnAddList);
        datePicker = findViewById(R.id.datepickerList);
        spinner = (Spinner) findViewById(R.id.dropdownList);

        toolbar = findViewById(R.id.addListToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddShoppingListActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Supermarkts));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String date = +datePicker.getMonth()+"-"+datePicker.getDayOfMonth();
                StringBuilder date = new StringBuilder();
                date.append(datePicker.getYear());
                date.append("-");
                if (datePicker.getMonth() < 10){
                    date.append(0);
                }
                date.append(datePicker.getMonth());
                date.append("-");
                if (datePicker.getDayOfMonth() < 10){
                    date.append(0);
                }
                date.append(datePicker.getDayOfMonth());

                String supermarkt = spinner.getSelectedItem().toString();

                addShoppinglist(getApplicationContext(), userID, date.toString(), supermarkt);
            }
        });

    }

    /**
     * This Method creates a new Shoppinglist for the Suer.
     * @param context the context
     * @param userID the users ID
     * @param date the Date when the User want to go Shopping
     * @param supermarkt the Supermarket of the Shopping trip
     */
    private void addShoppinglist(final Context context, final int userID, final String date, final String supermarkt){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error") == false){
                                openMainActivity();
                            }else{
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userID", Integer.toString(userID));
                params.put("date", date);
                params.put("supermarkt", supermarkt);
                return params;
            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    /**
     * start the Main Activity
     */
    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}