package com.example.hwr_huschka;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hwr_huschka.klassen.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper {

    public static void searchProduct(final Context context, final String partOfProductName, final ListView listView){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<Product> productArrayList = new ArrayList<Product>();

                            // fetch the Product data from JSON
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int produktID = jsonObject.getInt("ProduktID");
                                String hersteller = jsonObject.getString("Hersteller");
                                String name = jsonObject.getString("Name");
                                String kategorie = jsonObject.getString("Kategorie");
                                double preis = jsonObject.getDouble("Preis");
                                int kcal;
                                if (jsonObject.isNull("Kcal")){
                                    kcal = 0;
                                }else{
                                    kcal = jsonObject.getInt("Kcal");
                                }

                                // generate Product Object from Data
                                Product temp = new Product(produktID, hersteller, name, kategorie, preis, kcal);

                                // add to the Product ArrayList
                                productArrayList.add(temp);
                            }

                            // in der ListView anzeigen
                            ProductAdapter adapter;
                            adapter = new ProductAdapter(context, R.layout.listadapter_product, productArrayList);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Name", partOfProductName);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    /*
    public static void searchProductWithNumberField(final Context context, final String partOfProductName, final ListView listView){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<Product> productArrayList = new ArrayList<Product>();

                            // fetch the Product data from JSON
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int produktID = jsonObject.getInt("ProduktID");
                                String hersteller = jsonObject.getString("Hersteller");
                                String name = jsonObject.getString("Name");
                                String kategorie = jsonObject.getString("Kategorie");
                                double preis = jsonObject.getDouble("Preis");
                                int kcal;
                                if (jsonObject.isNull("Kcal")){
                                    kcal = 0;
                                }else{
                                    kcal = jsonObject.getInt("Kcal");
                                }

                                // generate Product Object from Data
                                Product temp = new Product(produktID, hersteller, name, kategorie, preis, kcal);

                                // add to the Product ArrayList
                                productArrayList.add(temp);
                            }

                            // in der ListView anzeigen
                            ProductNumberAdapter adapter;
                            adapter = new ProductNumberAdapter(context, R.layout.listadapter_product_spinner, productArrayList);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Name", partOfProductName);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }*/

}
