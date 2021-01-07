package com.example.hwr_huschka.Fragments.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;



import com.example.hwr_huschka.Activities.AddShoppingListActivity;
import com.example.hwr_huschka.Activities.ImpressumActivity;
import com.example.hwr_huschka.Activities.ProspektActivity;
import com.example.hwr_huschka.Activities.SettingsActivity;

import com.example.hwr_huschka.Activities.TippActivity;

import com.example.hwr_huschka.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    ImageButton btn_settings, btn_tipp, btn_impressum, btn_prospekt, btn_addshoppinglist, btn_search;

    BottomNavigationView bottomNav;

    TextView tv;

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        bottomNav = v.findViewById(R.id.bottom_navigation);

        btn_settings=v.findViewById(R.id.IB_Home_Einstellungen);
        btn_tipp=v.findViewById(R.id.IB_Home_Tipps);
        btn_impressum=v.findViewById(R.id.IB_Home_Impressum);
        btn_prospekt=v.findViewById(R.id.IB_Home_Prospekt);
        btn_addshoppinglist=v.findViewById(R.id.IB_Home_Liste_erstellen);
        btn_search=v.findViewById(R.id.IB_Home_Suche);



        sharedPreferences = this.getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);

        tv = v.findViewById(R.id.textView_home);
        tv.setText("Hey " + sharedPreferences.getString("firstname", "") + "!");

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        btn_tipp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TippActivity.class);
                startActivity(intent);
            }
        });

        btn_impressum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImpressumActivity.class);
                startActivity(intent);
            }
        });

        btn_prospekt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProspektActivity.class);
                startActivity(intent);
            }
        });

        btn_addshoppinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddShoppingListActivity.class);
                startActivity(intent);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new ProductFragment()).commit();
            }
        });





        return v;
    }

}
