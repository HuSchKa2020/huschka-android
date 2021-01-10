package com.example.hwr_huschka.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hwr_huschka.Constants;
import com.example.hwr_huschka.DatabaseHelper;
import com.example.hwr_huschka.ListAdapter.ProductAdapter;

import com.example.hwr_huschka.ListAdapter.ProductNumberAdapter;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.RequestHandler;
import com.example.hwr_huschka.klassen.Product;
import com.example.hwr_huschka.klassen.ShoppingList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductsToListActivity extends AppCompatActivity {

    ShoppingList shoppingList;

    EditText ed_productName;

    Button btn_SearchProduct, btn_Finish;

    ListView listViewProductSuche, listViewProductShoppinglist;
    ProductAdapter adapter;
    ProductNumberAdapter productNumberAdapter;

    HashMap<Product, Integer> newProductsOfShoppingList;
    HashMap<Product, Integer> productsBefore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproducttoshoppinglist);

        newProductsOfShoppingList = new HashMap<Product, Integer>();
        // get Data from intent
        shoppingList = (ShoppingList) this.getIntent().getSerializableExtra("shoppinglist");
        // EditText
        ed_productName = findViewById(R.id.edTextProduct);
        // Buttons
        btn_SearchProduct = findViewById(R.id.btnProductSearch);
        btn_Finish = findViewById(R.id.btnfinish);
        // ListViews
        listViewProductSuche = findViewById(R.id.produktListView);
        listViewProductShoppinglist = findViewById(R.id.listViewProductShoppinglist);
        // load Products from Shoppinglist to ListView
        productsBefore = (HashMap<Product, Integer>) this.getIntent().getSerializableExtra("productMap");
        if (productsBefore.size() > 0) {
            refreshProductListView();
        } else {
            productNumberAdapter = new ProductNumberAdapter(this, new HashMap<Product, Integer>(), true);
            listViewProductShoppinglist.setAdapter(productNumberAdapter);
        }

        DatabaseHelper.deleteProductsOfShoppinglist(getApplicationContext(), shoppingList.getListenID());

        btn_SearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchedProduct = ed_productName.getText().toString().trim();
                DatabaseHelper.searchProduct(getParent(), searchedProduct, listViewProductSuche);
            }
        });

        btn_Finish.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete All Old Items of the Shoppinglist in the Databae

                HashMap<Product, Integer> data = new HashMap<Product, Integer>();
                ArrayList<Product> productsForRemove = new ArrayList<>();

                if (productNumberAdapter != null) {
                    if (productNumberAdapter.getProductsOfList() != null) {
                        data = productNumberAdapter.getProductsOfList();
                    }
                }

                Iterator it = data.entrySet().iterator();
                while (it.hasNext()) {

                    Map.Entry item = (Map.Entry) it.next();

                    if((Integer)item.getValue() <= 0) {
                        it.remove();
                    }
                }

                addProductToList(getApplicationContext(), shoppingList.getListenID(), data);
            }
        }));

        listViewProductSuche.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product clickedItem = (Product) adapterView.getItemAtPosition(i);
                HashMap<Product, Integer> products =  productNumberAdapter.getProductsOfList();
                if(isProductInList(clickedItem, products)){
                    Toast.makeText(AddProductsToListActivity.this, "Produkt schon in ihrer Einkaufsliste!", Toast.LENGTH_SHORT).show();
                } else {

                    newProductsOfShoppingList.put(clickedItem, 0);
                    refreshProductListView();
                }


            }
        });
    }

    private boolean isProductInList(Product product, HashMap<Product, Integer> items){
        boolean contains = false;
        for (Product p:items.keySet()) {
            if(p.getProduktID() == product.getProduktID()){
                contains = true;
            }
        }
        return contains;
    }

    /**
     * Show all Products of the Shoppinglist to the ListView
     */
    private void refreshProductListView() {
        HashMap<Product, Integer> toShow = new HashMap<Product, Integer>();

        if (productsBefore.size() > 0) {
            toShow.putAll(productsBefore);
        }
        if (newProductsOfShoppingList.size() != 0) {
            toShow.putAll(newProductsOfShoppingList);
        }

        if (toShow.size() != 0) {
            productNumberAdapter = new ProductNumberAdapter(this, toShow, true);
            listViewProductShoppinglist.setAdapter(productNumberAdapter);
            productNumberAdapter.notifyDataSetChanged();
        }
    }

    /**
     * This Method add one Product to a Shoppinglist
     *
     * @param context the Context
     * @param data    a HashMap with the products of the Shoppinglist and the number Of
     */
    public void addProductToList(final Context context, final int listenID, final HashMap<Product, Integer> data) {

        final JSONArray products = new JSONArray();
        for (Map.Entry<Product, Integer> entry : data.entrySet()) {
            Product key = entry.getKey();
            Integer value = entry.getValue();
            // Build JsonArray with all Products
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put(Constants.REQ_PARAM_PRODUCTID, key.getProduktID());
                jsonObject.put(Constants.REQ_PARAM_NUMBEROF_PRODUCTS, value);

                products.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_PRODUCT_SHOPPINGLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("error") == true) {
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                            // go back to the Shoppinglist Overview
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("productMap", data); // set new List as Extra to the Intent
                            if (jsonObject.isNull(Constants.REQ_RETURN_SHOPPINGLIST_PRICE)){
                                resultIntent.putExtra("price", 0.00);
                            } else {
                                resultIntent.putExtra("price", jsonObject.getDouble(Constants.REQ_RETURN_SHOPPINGLIST_PRICE));
                            }

                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();


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
                params.put(Constants.REQ_PARAM_PRODUCT_ARRAY, products.toString());
                params.put(Constants.REQ_PARAM_SHOPPINGLISTID, Integer.toString(listenID));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

}
