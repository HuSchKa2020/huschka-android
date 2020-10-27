package com.example.hwr_huschka.Activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hwr_huschka.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TippActivity extends AppCompatActivity {

    String[] ListViewTippNummer = new String[]{
            "Tipp 1","Tipp 2","Tipp 3","Tipp 4","Tipp 5","Tipp 6","Tipp 7","Tipp 8","Tipp 9","Tipp 10"
    };

    String[] ListViewTipp = new String[]{
            "Nutzt Huschak!!","Meide lebensmittel","shsdgfhngfds","gbsdjfg fgdfsgsfdhg kikikiki","hngtzhntzb  sdfsdgf _ ddd","Tthdtrhthbdgh uzitzu","gghghfg","Tserfef","qwertzuio","12345678"
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipps);

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String, String>>();
        for (int x = 0; x < 10; x++){
            HashMap<String,String> hm = new HashMap<String,String>();
            hm.put("ListTitel",ListViewTippNummer[x]);
            hm.put("ListText",ListViewTipp[x]);
            aList.add(hm);
        }

        String[] from = {
                "ListTitel", "ListText"
        };
        int[] to = {
                R.id.TV_adapter_TippNummer,R.id.TV_adapter_TippText
        };

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(),aList, R.layout.listadapter_tipps,from,to);
        ListView simpleListView = (ListView)findViewById(R.id.LV_Tipps);
        simpleListView.setAdapter(simpleAdapter);
        
    }
}
