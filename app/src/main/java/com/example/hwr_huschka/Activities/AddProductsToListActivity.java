package com.example.hwr_huschka.Activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hwr_huschka.FragmentsMain.ShoppingListFragment;
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



        /*Spinner mySpinner = (Spinner) findViewById(R.id.spinner_ProduktAnzahl);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddProductsToListActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Anzahl));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        */
    }
}
