package com.example.hwr_huschka.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hwr_huschka.ListAdapter.ProductCheckboxSpinnerPositionAdapter;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.Position;
import com.example.hwr_huschka.klassen.ProductInShoppinglist;

import java.util.ArrayList;

public class GoShoppingActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView produktListView;
    Button btn_einkaufBeenden;
    int shoppingListID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_shopping);

        // get ListenID from Intent
        shoppingListID = getIntent().getIntExtra("shoppingListID", 0);


        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        produktListView = findViewById(R.id.LV_go_shopping);
        btn_einkaufBeenden = findViewById(R.id.btn_Einkauf_beenden);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case (android.R.id.home):
                finish();
                break;
        }

        return true;
    }
}
