package com.example.hwr_huschka.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hwr_huschka.DatabaseHelper;
import com.example.hwr_huschka.ListAdapter.ProductAdapter;

import com.example.hwr_huschka.ListAdapter.ProductNumberAdapter;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.Product;
import com.example.hwr_huschka.klassen.ShoppingList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
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
        }

        DatabaseHelper.deleteProductsOfShoppinglist(getApplicationContext(), shoppingList.getListenID());

        btn_SearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchedProduct = ed_productName.getText().toString().trim();
                DatabaseHelper.searchProduct(getApplicationContext(), searchedProduct, listViewProductSuche);
            }
        });

        btn_Finish.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete All Old Items of the Shoppinglist in the Databae

                HashMap<Product, Integer> data = new HashMap<Product, Integer>();
                ArrayList<Product> productsForRemove = new ArrayList<>();
                // get new List of Items
                if (productNumberAdapter != null) {
                    if (productNumberAdapter.getProductsOfList() != null) {
                        data = productNumberAdapter.getProductsOfList();
                        JSONArray jsonArray = new JSONArray();
                        // pull them to the Database
                        for (Map.Entry<Product, Integer> entry : data.entrySet()) {
                            Product key = entry.getKey();
                            Integer value = entry.getValue();
                            if (value > 0) {
                                // Build JsonArray with all Products
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("ListenID", shoppingList.getListenID());
                                    jsonObject.put("ProduktID", key.getProduktID());
                                    jsonObject.put("numberOf", value);

                                    jsonArray.put(jsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                productsForRemove.add(key);
                            }
                        }
                        // send JsonArrayToBackend
                        DatabaseHelper.addProductToList(getApplicationContext(), jsonArray);
                    }
                }


                for (Product p : productsForRemove) {
                    data.remove(p);
                }

                // go back to the Shoppinglist Overview
                Intent resultIntent = new Intent();
                resultIntent.putExtra("productMap", data); // set new List as Extra to the Intent
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }));

        listViewProductSuche.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product clickedItem = (Product) adapterView.getItemAtPosition(i);
                newProductsOfShoppingList.put(clickedItem, 0);
                refreshProductListView();
            }
        });
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

}
