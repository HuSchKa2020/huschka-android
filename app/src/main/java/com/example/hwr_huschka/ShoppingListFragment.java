package com.example.hwr_huschka;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShoppingListFragment extends Fragment {

    FloatingActionButton addListFltBtn;
    ListView listView;
    EditText editText;

    ArrayList<String> list;
    ArrayAdapter<String> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shoppinglists, container, false);
        editText = v.findViewById(R.id.textListName);
        addListFltBtn = v.findViewById(R.id.floatActBtnAddShoopingList);

        listView = (ListView) v.findViewById(R.id.shoppingListView);


        list = new ArrayList<String>();

       arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);

        listView.setAdapter(arrayAdapter);

        addListFltBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Fragment aufrufen, in dem Infos für eine neue Einkaufsliste eingetragen werden
                // vereinfacht erstmal nur neue Liste in das LinearLayout hinzufügen

                startActivity(new Intent(getActivity(), AddShoppingList.class));

                /*list.add(editText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                */
            }
        });
        return v;
    }

}
