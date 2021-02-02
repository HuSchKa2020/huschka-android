package com.example.hwr_huschka.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hwr_huschka.ListAdapter.ProductCheckboxSpinnerPositionAdapter;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.ProductInShoppinglist;

import java.util.ArrayList;

public class FinishShoppingActivity extends AppCompatActivity {

    Toolbar toolbar;

    ProductCheckboxSpinnerPositionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finisch_shopping);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case (android.R.id.home):
                finish();
                break;
        }

        return true;
    }
}