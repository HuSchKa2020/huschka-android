package com.example.hwr_huschka.Activities;

import android.os.Bundle;
import android.widget.Toast;

import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.ShoppingList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductsToListActivity extends AppCompatActivity {

    ShoppingList shoppingList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_produkte);
        shoppingList = (ShoppingList) this.getIntent().getSerializableExtra("shoppinglist");

    }
}
