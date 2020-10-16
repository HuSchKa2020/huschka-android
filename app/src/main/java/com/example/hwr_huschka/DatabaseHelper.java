package com.example.hwr_huschka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    /**
     * This Method add one Product to a Shoppinglist
     * @param context the Context
     * @param shoppingListID the ID of the Shoppinglist
     * @param productID the ID of the Product
     * @param numberOf number of Products
     */
    public static void addProductToList(final Context context, final int shoppingListID, final int productID, final int numberOf){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_PRODUCT_SHOPPINGLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){
                                Toast.makeText(context, "ListID: " + shoppingListID + ", ProduktID: " + productID + ", Anzahl: " + numberOf, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "error: " + productID, Toast.LENGTH_SHORT).show();
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
                params.put("listenID", Integer.toString(shoppingListID));
                params.put("produktID", Integer.toString(productID));
                params.put("anzahl", Integer.toString(numberOf));
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

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
                params.put("listenid", Integer.toString(listenID));
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }



}