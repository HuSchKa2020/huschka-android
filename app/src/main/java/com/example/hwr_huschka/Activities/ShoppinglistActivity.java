package com.example.hwr_huschka.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ShoppinglistActivity extends AppCompatActivity {

    ListView listView;
    TextView tv_supermarkt, tv_datum, tv_price, tv_summe, tv_GesundheitsScore, tv_UmweltScore, tv_GesamtScore, tv_ernaehrungsform;

    FloatingActionButton fabToAddProd, fabStartShopping, fabDelete;
    ProductNumberAdapter adapter = new ProductNumberAdapter(this, new HashMap<Product, Integer>());

    ShoppingList shoppingList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_supermarkt = findViewById(R.id.TV_shoppinglist_SupermarktAuswahl);
        tv_datum = findViewById(R.id.TV_shoppinglist_DatumAuswahl);
        tv_price = findViewById(R.id.TV_shoppinglist_Preis);
        tv_summe = (TextView) findViewById(R.id.TV_Summe);
        tv_summe.setPaintFlags(tv_price.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_GesundheitsScore = findViewById(R.id.TV_shoppinglist_GesundheitsScore_anzeige);
        tv_UmweltScore = findViewById(R.id.TV_shoppinglist_UmweltScore_anzeige);
        tv_GesamtScore = findViewById(R.id.TV_shoppinglist_GesamtScore_anzeige);
        tv_ernaehrungsform = findViewById(R.id.TV_shoppinglist_Ernaehrungsform);

        shoppingList = (ShoppingList) this.getIntent().getSerializableExtra("shoppinglist");
        tv_supermarkt.setText(shoppingList.getSupermarkt());
        tv_datum.setText(shoppingList.getDatum().toString());

        fabToAddProd = findViewById(R.id.fab_toAddProd);
        fabStartShopping = findViewById(R.id.fab_startShopping);
        fabDelete = findViewById(R.id.fab_deleteList);


        listView = (ListView) findViewById(R.id.LV_shoppinglist_ProduktListe);
        loadProductsOfShoppinglist(this, shoppingList.getListenID(), listView);

        fabToAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProductsToListActivity.class);
                intent.putExtra("shoppinglist", shoppingList);
                ProductNumberAdapter productNumberAdapter = (ProductNumberAdapter) listView.getAdapter();
                if (productNumberAdapter != null) {
                    intent.putExtra("productMap", productNumberAdapter.getProductsOfList());
                }
                startActivityForResult(intent, 0);
            }
        });

        fabStartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GoShoppingActivity.class);
                startActivity(intent);
                Toast.makeText(ShoppinglistActivity.this, "Start Shopping", Toast.LENGTH_SHORT).show();
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper.deleteShoppinglist(getApplicationContext(), shoppingList.getListenID());

                Toast.makeText(getApplicationContext(), "Ihre Einkaufsliste wurde gelöscht", Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("ListenID", shoppingList.getListenID());
                setResult(100, resultIntent); // 100 für Liste gelöscht
                finish();
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
            try {
                setTVScores(new JSONObject(data.getStringExtra("Scores")));
            } catch (JSONException e) {
                System.out.println(e.toString());
            }


            double preis = data.getDoubleExtra("price", 0.00);
            tv_price.setText(Double.toString(Math.round(100.0 * preis) / 100.0));
        }
    }

    public void setTVScores (JSONObject scores) throws JSONException {
        tv_GesundheitsScore.setText(Double.toString(Math.round(scores.getDouble(Constants.REQ_RETURN_GESUNDHEITSSCORE) * 100.0) / 100.0));
        tv_UmweltScore.setText(Double.toString(Math.round(scores.getDouble(Constants.REQ_RETURN_UMWELTSCORE) * 100.0) / 100.0));
        tv_GesamtScore.setText(Double.toString(Math.round(scores.getDouble(Constants.REQ_RETURN_GESAMTSCORE) * 100.0) / 100.0));
        tv_ernaehrungsform.setText(scores.getString(Constants.REQ_RETURN_ERNAEHRUNGSFORM));
    }
    
    public void loadProductsOfShoppinglist(final Context context, final int shoppingListID, final ListView listView){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_PRODUCT_OF_SHOPPINGLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            HashMap<Product, Integer> products = new HashMap<Product, Integer>();
                            if(!response.equals("")) {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray produkte = jsonObject.getJSONArray(Constants.REQ_RETURN_PRODUKT_LISTE);
                                JSONObject scores = jsonObject.getJSONObject(Constants.REQ_RETURN_ALL_SCORES);

                                // set Scores
                                setTVScores(scores);

                                // fetch the Product data from JSON
                                for (int i = 0; i < produkte.length(); i++) {

                                    JSONObject produkt = produkte.getJSONObject(i);

                                    int productID = produkt.getInt(Constants.REQ_RETURN_PRODUKTID);
                                    String hersteller = produkt.getString(Constants.REQ_RETURN_PRODUKT_PRODUCER);
                                    String name = produkt.getString(Constants.REQ_RETURN_PRODUKT_NAME);
                                    //String kategorie = produkt.getString(Constants.REQ_RETURN_PRODUKT_KATEGORIE);
                                    double preis = produkt.getDouble(Constants.REQ_RETURN_PRODUKT_PRICE);
                                    int kcal = 0;
                                    if (!produkt.isNull(Constants.REQ_RETURN_PRODUKT_KCAL)) {
                                        kcal = produkt.getInt(Constants.REQ_RETURN_PRODUKT_KCAL);
                                    }


                                    int numberOf = produkt.getInt(Constants.REQ_RETURN_SHOPPINGLIST_NUMBEROF_PRODUCTS);

                                    Product temp = new Product(productID, hersteller, name, "test", preis, kcal);

                                    // add to the Product to the HashMap
                                    products.put(temp, numberOf);
                                }
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case (android.R.id.home):
                finish();
                break;
        }

        return true;
    }
}