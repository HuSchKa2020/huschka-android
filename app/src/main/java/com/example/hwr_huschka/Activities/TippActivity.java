package com.example.hwr_huschka.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hwr_huschka.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TippActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipps);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] ListViewTipp = this.getResources().getStringArray(R.array.Tipps);

        ArrayList<String> aList = new ArrayList<String>();
        for (int x = 1; x <= ListViewTipp.length; x++){
            aList.add("Tipp " + x +": "+ ListViewTipp[x-1]);
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, aList);



        ListView simpleListView = (ListView)findViewById(R.id.LV_Tipps);
        simpleListView.setAdapter(adapter);

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
