package com.example.hwr_huschka.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hwr_huschka.DatabaseHelper;
import com.example.hwr_huschka.ProductAdapter;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hwr_huschka.FragmentsMain.ShoppingListFragment;
import com.example.hwr_huschka.ProductNumberAdapter;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.Product;
import com.example.hwr_huschka.klassen.ShoppingList;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproducttoshoppinglist);
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
        if (shoppingList.getInhalt() != null || shoppingList.getInhalt().size()>0){
            refreshProductListView();
        }

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
                // go Back to List Overview and refresh the List in Database
                HashMap<Product, Integer> data = productNumberAdapter.getProductsOfList();

                for (Map.Entry<Product, Integer> entry : data.entrySet()) {
                    Product key = entry.getKey();
                    Integer value = entry.getValue();
                    DatabaseHelper.addProductToList(getApplicationContext(), shoppingList.getListenID(), key.getProduktID(), value);
                }
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

    private void refreshProductListView(){
        HashMap<Product, Integer> toShow = new HashMap<Product, Integer>();

        if (shoppingList.getInhalt() != null){
            toShow.putAll(shoppingList.getInhalt());
        }
        if (newProductsOfShoppingList.size() != 0){
            toShow.putAll(newProductsOfShoppingList);
        }

       if (toShow.size() != 0){
           productNumberAdapter = new ProductNumberAdapter(this,  toShow);
           listViewProductShoppinglist.setAdapter(productNumberAdapter);
           productNumberAdapter.notifyDataSetChanged();
        }
    }

}
