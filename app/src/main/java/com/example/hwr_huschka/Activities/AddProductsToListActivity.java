package com.example.hwr_huschka.Activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hwr_huschka.DatabaseHelper;
import com.example.hwr_huschka.ProductAdapter;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.Product;
import com.example.hwr_huschka.klassen.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductsToListActivity extends AppCompatActivity {

    ShoppingList shoppingList;

    ListView listViewProductSuche, listViewProductShoppinglist;
    ProductAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproducttoshoppinglist);

        shoppingList = (ShoppingList) this.getIntent().getSerializableExtra("shoppinglist");

        listViewProductSuche = findViewById(R.id.produktListView);
        listViewProductShoppinglist = findViewById(R.id.listViewProductShoppinglist);

        fillShoppinglistListView();

    }

    private void fillShoppinglistListView(){
        ArrayList<Product> list = new ArrayList<Product>();
        list.add(new Product(12, "Heinz","Ketchup", "Soße", 1.99, 300));
        // list.add(new Product(13, "Mayo", 1.99));
        // list.add(new Product(14, "Nudeln", 1.99));
        // list.add(new Product(15, "soße", 1.99));
        shoppingList.setInhalt(list);

        // list = shoppingList.getInhalt();

        adapter = new ProductAdapter(this, R.layout.listadapter_product, list);
        listViewProductShoppinglist.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
