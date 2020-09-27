package com.example.hwr_huschka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ProductInfoActivity extends AppCompatActivity {

    TextView tvProductID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        Toolbar toolbar = findViewById(R.id.addListToolbar);
        tvProductID = findViewById(R.id.TVproductID);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvProductID.setText("ID: " + getIntent().getStringExtra("productID"));
    }
}