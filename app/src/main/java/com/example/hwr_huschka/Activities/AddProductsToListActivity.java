package com.example.hwr_huschka.Activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.Product;
import com.example.hwr_huschka.klassen.ShoppingList;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductsToListActivity extends AppCompatActivity {

    ShoppingList shoppingList;

    List<Product> checkedItems;

    ListView listViewProdcts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproducttoshoppinglist);

        shoppingList = (ShoppingList) this.getIntent().getSerializableExtra("shoppinglist");
        checkedItems = shoppingList.getInhalt();

        listViewProdcts = findViewById(R.id.produktListView);


    }
}
