package com.example.hwr_huschka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

public class AddShoppingList extends AppCompatActivity {

    Button btnAddList;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);

        btnAddList = findViewById(R.id.btnAddList);
        datePicker = findViewById(R.id.datepickerList);
        Toolbar toolbar = findViewById(R.id.addListToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}