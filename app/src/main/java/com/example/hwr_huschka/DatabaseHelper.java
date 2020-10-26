package com.example.hwr_huschka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hwr_huschka.Activities.MainActivity;
import com.example.hwr_huschka.ListAdapter.ProductAdapter;
import com.example.hwr_huschka.ListAdapter.ProductNumberAdapter;
import com.example.hwr_huschka.klassen.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for Request to the Database of the Apache Webserver
 */
public class DatabaseHelper {

    /**
     * Search Product by a Part of a Productname
     * @param context the context
     * @param partOfProductName the String who is searched for
     * @param listView the ListView where the Products will be Shown
     */
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

                                int produktID = jsonObject.getInt(Constants.REQ_RETURN_PRODUKTID);
                                String hersteller = jsonObject.getString(Constants.REQ_RETURN_PRODUKT_PRODUCER);
                                String name = jsonObject.getString(Constants.REQ_RETURN_PRODUKT_NAME);
                                String kategorie = jsonObject.getString(Constants.REQ_RETURN_PRODUKT_KATEGORIE);
                                double preis = jsonObject.getDouble(Constants.REQ_RETURN_PRODUKT_PRICE);
                                int kcal;
                                if (jsonObject.isNull(Constants.REQ_RETURN_PRODUKT_KCAL)){
                                    kcal = 0;
                                }else{
                                    kcal = jsonObject.getInt(Constants.REQ_RETURN_PRODUKT_KCAL);
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
                params.put(Constants.REQ_PARAM_PRODUCT_TEILNAME, partOfProductName);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }
  
  /**
     * This Method add one Product to a Shoppinglist
     * @param context the Context
     * @param jsonArray a JSONArray with the products of the Shoppinglist
     */
    public static void addProductToList(final Context context, final JSONArray jsonArray){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_PRODUCT_SHOPPINGLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error") == true){
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }

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
                params.put(Constants.REQ_PARAM_PRODUCT_ARRAY, jsonArray.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }


    /**
     * This Method delete all Products of the Shoppinglist.
     * @param context the Context
     * @param listenID the ID of the Shopppinglist.
     */
    public static void deleteProductsOfShoppinglist(final Context context, final int listenID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DELETE_ALL_PRODUCTS_FROM_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("error") == false){

                            } else{
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
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
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }



}
