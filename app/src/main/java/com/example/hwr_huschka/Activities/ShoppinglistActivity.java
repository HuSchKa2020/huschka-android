package com.example.hwr_huschka.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hwr_huschka.Constants;
import com.example.hwr_huschka.DatabaseHelper;
import com.example.hwr_huschka.ListAdapter.ProductNumberAdapter;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.RequestHandler;
import com.example.hwr_huschka.klassen.Product;
import com.example.hwr_huschka.klassen.ShoppingList;
import com.github.clans.fab.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShoppinglistActivity extends AppCompatActivity {

    ListView listView;
    TextView tv_listID, tv_supermarkt, tv_datum, tv_price, tv_summe;

    FloatingActionButton fabToAddProd, fabStartShopping;
    ProductNumberAdapter adapter = new ProductNumberAdapter(this, new HashMap<Product, Integer>());

    ShoppingList shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);

        tv_listID = findViewById(R.id.TV_shoppinglist_ID);
        tv_supermarkt = findViewById(R.id.TV_shoppinglist_SupermarktAuswahl);
        tv_datum = findViewById(R.id.TV_shoppinglist_DatumAuswahl);
        tv_price = findViewById(R.id.TV_shoppinglist_Preis);
        tv_summe = (TextView) findViewById(R.id.TV_Summe);
        tv_summe.setPaintFlags(tv_price.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        shoppingList = (ShoppingList) this.getIntent().getSerializableExtra("shoppinglist");
        tv_listID.setText(Integer.toString(shoppingList.getListenID()));
        tv_supermarkt.setText(shoppingList.getSupermarkt());
        tv_datum.setText(shoppingList.getDatum().toString());

        fabToAddProd = findViewById(R.id.fab_toAddProd);
        fabStartShopping = findViewById(R.id.fab_startShopping);


        listView = (ListView) findViewById(R.id.LV_shoppinglist_ProduktListe);
        loadProductsOfShoppinglist(this, shoppingList.getListenID(), listView);

        fabToAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProductsToListActivity.class);
                intent.putExtra("shoppinglist", shoppingList);
                ProductNumberAdapter productNumberAdapter = (ProductNumberAdapter) listView.getAdapter();
                intent.putExtra("productMap", productNumberAdapter.getProductsOfList());
                startActivityForResult(intent, 0);
            }
        });

        fabStartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start Shopping
                Toast.makeText(ShoppinglistActivity.this, "Start Shopping", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ProductInfoActivity.class);

                ArrayList<Product> data = new ArrayList<Product>(adapter.getProductsOfList().keySet());
                Integer id = data.get(i).getProduktID();

                intent.putExtra("productID", Integer.toString(id));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_CANCELED
            && requestCode == 0
                && data != null) {

            // show new List in ListView
            HashMap<Product, Integer> products = (HashMap<Product, Integer>) data.getSerializableExtra("productMap");
            adapter = new ProductNumberAdapter(ShoppinglistActivity.this, products);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            double preis = data.getDoubleExtra("price", 0.00);
            tv_price.setText(Double.toString(Math.round(100.0 * preis) / 100.0));
        }
    }
    
    public void loadProductsOfShoppinglist(final Context context, final int shoppingListID, final ListView listView){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_PRODUCT_OF_SHOPPINGLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            HashMap<Product, Integer> products = new HashMap<Product, Integer>();

                            // fetch the Product data from JSON
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int productID = jsonObject.getInt(Constants.REQ_RETURN_PRODUKTID);
                                String hersteller = jsonObject.getString(Constants.REQ_RETURN_PRODUKT_PRODUCER);
                                String name = jsonObject.getString(Constants.REQ_RETURN_PRODUKT_NAME);
                                //String kategorie = jsonObject.getString(Constants.REQ_RETURN_PRODUKT_KATEGORIE);
                                double preis = jsonObject.getDouble(Constants.REQ_RETURN_PRODUKT_PRICE);
                                int kcal = 0;
                                if(!jsonObject.isNull(Constants.REQ_RETURN_PRODUKT_KCAL)){
                                    kcal = jsonObject.getInt(Constants.REQ_RETURN_PRODUKT_KCAL);
                                }


                                int numberOf = jsonObject.getInt(Constants.REQ_RETURN_SHOPPINGLIST_NUMBEROF_PRODUCTS);

                                Product temp = new Product(productID, hersteller, name, "test", preis, kcal);

                                // add to the Product to the HashMap
                                products.put(temp, numberOf);
                            }

                            // in der ListView anzeigen
                            adapter = new ProductNumberAdapter(context, products);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            getPriceOfShoppinglist(context, shoppingListID);

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
                params.put(Constants.REQ_PARAM_SHOPPINGLISTID, Integer.toString(shoppingListID));
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10*1000, 20,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void getPriceOfShoppinglist(final Context context, final int shoppingListID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_PRICE_OF_SHOPPINGLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            tv_price.setText("0.00");
                            if (!jsonObject.isNull(Constants.REQ_RETURN_SHOPPINGLIST_PRICE)){
                                double preis = Double.parseDouble(jsonObject.getString(Constants.REQ_RETURN_SHOPPINGLIST_PRICE));
                                tv_price.setText(Double.toString(Math.round(100.0 * preis) / 100.0));
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
                params.put(Constants.REQ_PARAM_SHOPPINGLISTID, Integer.toString(shoppingListID));
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10*1000, 20,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

}