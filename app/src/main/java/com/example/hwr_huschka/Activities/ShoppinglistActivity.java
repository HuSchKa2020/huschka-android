package com.example.hwr_huschka.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.ShoppingList;

import java.util.ArrayList;

public class ShoppinglistActivity extends AppCompatActivity {

    ListView listView;
    TextView tv_listID, tv_supermarkt, tv_datum;

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



        listView = (ListView) findViewById(R.id.LV_shoppinglist_ProduktListe);

        ArrayList<String> arrayList=new ArrayList<>();

        arrayList.add("Heinz Ketchup");
        arrayList.add("Kekse");
        arrayList.add("Schweinefilet");
        arrayList.add("Chips");
        arrayList.add("Goldkrone");
        arrayList.add("Klopapier");
        arrayList.add("Brötchen");
        arrayList.add("Kartoffeln");

        ArrayAdapter arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);





    }


}