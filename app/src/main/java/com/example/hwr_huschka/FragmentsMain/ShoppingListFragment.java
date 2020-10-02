package com.example.hwr_huschka.FragmentsMain;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hwr_huschka.Activities.AddShoppingListActivity;
import com.example.hwr_huschka.Activities.ProductInfoActivity;
import com.example.hwr_huschka.Activities.ShoppinglistActivity;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.ShoppingListAdapter;
import com.example.hwr_huschka.klassen.ShoppingList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.threeten.bp.LocalDate;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class ShoppingListFragment extends Fragment {

    FloatingActionButton addListFltBtn;
    ListView listView;

    ArrayList<ShoppingList> listOfShoppingLists;
    ShoppingListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shoppinglists, container, false);

        addListFltBtn = v.findViewById(R.id.floatActBtnAddShoopingList);
        listView = (ListView) v.findViewById(R.id.shoppingListView);

        listOfShoppingLists = loadShoppingLists(0); // here in Future add the ID from the SharedPreferences Class


    //    LocalDate datum = LocalDate.now();
        ShoppingList shoppingList = new ShoppingList("LIDL", "bezahlt");
        listOfShoppingLists.add(shoppingList);

        adapter = new ShoppingListAdapter(getContext(), R.layout.listadapter_shoppinglist, listOfShoppingLists);
        listView.setAdapter(adapter);

        addListFltBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Fragment aufrufen, in dem Infos für eine neue Einkaufsliste eingetragen werden
                startActivity(new Intent(getActivity(), AddShoppingListActivity.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingList clickedItem = (ShoppingList) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), ShoppinglistActivity.class);

                intent.putExtra("shoppingListID", Integer.toString(clickedItem.getListenID()));
                startActivity(intent);
            }
        });


        return v;
    }

    /**
     * Methode gibt eine ganze Liste mit ShoppingList Objekten des Kunden zurück
     *
     * @param kundenID ID des Kunden
     * @return Liste mit allen ShoppingLists des Kunden, die in der Datenbank gespeichert sind
     */
    private ArrayList<ShoppingList> loadShoppingLists(int kundenID){

        // have to be implemented if backend is ready

        return new ArrayList<ShoppingList>();
    }

}
