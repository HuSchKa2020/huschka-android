package com.example.hwr_huschka.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hwr_huschka.R;

import java.util.ArrayList;

public class ShoppinglistActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);


        listView= (ListView) findViewById(R.id.LV_shoppinglist_ProduktListe);

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