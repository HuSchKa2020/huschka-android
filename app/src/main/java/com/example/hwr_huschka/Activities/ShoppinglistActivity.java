package com.example.hwr_huschka.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hwr_huschka.DatabaseHelper;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.Product;
import com.example.hwr_huschka.klassen.ShoppingList;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShoppinglistActivity extends AppCompatActivity {

    ListView listView;
    TextView tv_listID, tv_supermarkt, tv_datum;

    FloatingActionButton fabToAddProd, fabStartShopping;

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

        fabToAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProductsToListActivity.class);

                intent.putExtra("shoppinglist", shoppingList);
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

        listView = (ListView) findViewById(R.id.LV_shoppinglist_ProduktListe);
        DatabaseHelper.loadProductsOfShoppinglist(this, shoppingList.getListenID(), listView);
        Toast.makeText(getApplicationContext(), shoppingList.getListenID() + "", Toast.LENGTH_LONG).show();

    }


}