package com.example.hwr_huschka.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hwr_huschka.Constants;
import com.example.hwr_huschka.DatabaseHelper;
import com.example.hwr_huschka.ListAdapter.ProductCheckboxSpinnerPositionAdapter;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.RequestHandler;
import com.example.hwr_huschka.klassen.Position;
import com.example.hwr_huschka.klassen.ProductInShoppinglist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoShoppingActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView produktListView;
    Button btn_einkaufBeenden;
    int shoppingListID;

    ProductCheckboxSpinnerPositionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_shopping);

        // get ListenID from Intent
        shoppingListID = getIntent().getIntExtra("shoppingListID", 0);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        produktListView = findViewById(R.id.LV_go_shopping);
        btn_einkaufBeenden = findViewById(R.id.btn_Einkauf_beenden);

        loadProductListGoShopping(GoShoppingActivity.this, shoppingListID, produktListView);


        btn_einkaufBeenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FinishShoppingActivity.class);
                startActivity(intent);

                // Fragment Dialog hier, wo man die Richtigkeit seiner Angaben bestätigt
                // nur wenn der Nutzer sie bestätigt, wir der folgende Code ausgeführt

                adapter = (ProductCheckboxSpinnerPositionAdapter) produktListView.getAdapter();

                ArrayList<ProductInShoppinglist> pr = adapter.getCheckedProducts();
                einkaufBeenden(GoShoppingActivity.this, shoppingListID, pr);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case (android.R.id.home):
                finish();
                break;
        }

        return true;
    }

    public static void loadProductListGoShopping(final Activity activity, final int listenID, final ListView listView) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SHOPPINGLIST_POSITIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            System.out.println(response);

                            ProductInShoppinglist[] products = new ProductInShoppinglist[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int productId = (int) jsonObject.get(Constants.REQ_RETURN_PRODUKTID);
                                String hersteller = (String) jsonObject.get(Constants.REQ_RETURN_PRODUKT_PRODUCER);
                                String name = (String) jsonObject.get(Constants.REQ_RETURN_PRODUKT_NAME);
                                String kategorie = (String) jsonObject.get(Constants.REQ_RETURN_PRODUKT_KATEGORIE);
                                double preis = (double) jsonObject.get(Constants.REQ_RETURN_PRODUKT_PRICE);

                                int kcal;

                                if (jsonObject.isNull(Constants.REQ_RETURN_PRODUKT_KCAL)) {
                                    kcal = 0;
                                } else {
                                    kcal = (int) jsonObject.get(Constants.REQ_RETURN_PRODUKT_KCAL);
                                }
                                // Position
                                int reihe = (int) jsonObject.get(Constants.REQ_RETURN_PRODUKT_REIHE);
                                int regalhoehe = (int) jsonObject.get(Constants.REQ_RETURN_PRODUKT_REGALHOEHE);

                                int anzahl = (int) jsonObject.get(Constants.REQ_RETURN_SHOPPINGLIST_NUMBEROF_PRODUCTS);

                                products[i] = new ProductInShoppinglist(productId, hersteller, name, kategorie, preis, kcal, new Position(reihe, regalhoehe), anzahl);
                            }

                            ProductCheckboxSpinnerPositionAdapter adapter = new ProductCheckboxSpinnerPositionAdapter(activity.getApplicationContext(), products);

                            listView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.REQ_PARAM_SHOPPINGLISTID, Integer.toString(listenID));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(activity.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    
    public void einkaufBeenden(final Activity activity, final int listenID, final ArrayList<ProductInShoppinglist> produkte) {

        final JSONArray produkteJSON;
        produkteJSON = productListToJSONArray(produkte);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_PRODUCT_SHOPPINGLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSON = new JSONObject(response);
                            Log.i("Response", response);

                            if (responseJSON.getBoolean("error") == false) {
                                double preis = responseJSON.getDouble("Gesamtpreis");

                                // Bezahl Activity starten
                                Intent intent = new Intent();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.REQ_PARAM_SHOPPINGLISTID, Integer.toString(listenID));
                params.put(Constants.REQ_PARAM_PRODUCT_ARRAY, produkteJSON.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(activity.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public static JSONArray productListToJSONArray(final ArrayList<ProductInShoppinglist> produkte) {
        JSONArray jsonArray = new JSONArray();

        try {
            for (ProductInShoppinglist produkt : produkte) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("ProduktID", Integer.toString(produkt.getProduktID()));
                jsonObject.put("Anzahl", Integer.toString(produkt.getAnzahl()));

                jsonArray.put(jsonObject);
            }
        } catch (JSONException exception) {
            Log.e("Fehler", exception.getMessage());
        }

        return jsonArray;
    }
}
