package com.example.hwr_huschka.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppinglistActivity extends AppCompatActivity {

    ListView listView;
    TextView tv_listID, tv_supermarkt, tv_datum;

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
                startActivity(intent);
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
                intent.putExtra("productID", adapterView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadProductsOfShoppinglist(this, shoppingList.getListenID(), listView);
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

                                int productID = jsonObject.getInt("ProduktID");
                                String hersteller = jsonObject.getString("Hersteller");
                                String name = jsonObject.getString("Name");
                                //String kategorie = jsonObject.getString("Kategorie");
                                double preis = jsonObject.getDouble("Preis");
                                int kcal = 0;
                                if(!jsonObject.isNull("Kcal")){
                                    kcal = jsonObject.getInt("Kcal");
                                }


                                int numberOf = jsonObject.getInt("Anzahl");

                                Product temp = new Product(productID, hersteller, name, "test", preis, kcal);

                                // add to the Product to the HashMap
                                products.put(temp, numberOf);
                            }

                            // in der ListView anzeigen
                            adapter = new ProductNumberAdapter(context, products);
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
                params.put("listenid", Integer.toString(shoppingListID));
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10*1000, 20,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }


}