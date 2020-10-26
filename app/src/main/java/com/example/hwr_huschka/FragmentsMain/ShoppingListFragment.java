package com.example.hwr_huschka.FragmentsMain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hwr_huschka.Activities.AddShoppingListActivity;
import com.example.hwr_huschka.Activities.ShoppinglistActivity;
import com.example.hwr_huschka.Constants;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.ListAdapter.ShoppingListAdapter;
import com.example.hwr_huschka.RequestHandler;
import com.example.hwr_huschka.klassen.ShoppingList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShoppingListFragment extends Fragment {

    FloatingActionButton addListFltBtn;
    ListView listView;

    ShoppingListAdapter adapter;

    SharedPreferences sharedPreferences;
    int userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shoppinglists, container, false);

        // get UserID from SharedPreferences
        sharedPreferences = this.getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt("id", 0);

        addListFltBtn = v.findViewById(R.id.floatActBtnAddShoopingList);
        listView = (ListView) v.findViewById(R.id.shoppingListView);

        loadShoppingLists(userID);

        // go to create new Shoppinglist
        addListFltBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Fragment aufrufen, in dem Infos für eine neue Einkaufsliste eingetragen werden
                startActivityForResult(new Intent(getActivity(), AddShoppingListActivity.class), 0);
            }
        });

        // go into teh View for One Shoppinglist
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingList clickedItem = (ShoppingList) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), ShoppinglistActivity.class);

                intent.putExtra("shoppinglist", clickedItem);
                startActivity(intent);

            }
        });



        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            /*ShoppingListAdapter adapter = (ShoppingListAdapter) listView.getAdapter();
            adapter.add((ShoppingList) data.getSerializableExtra("shoppinglist"));*/
        }
    }

    /**
     * Methode lädt alle Einkaufslisten in die ListView
     *
     * @param kundenID ID des Kunden
     */
    private void loadShoppingLists(final int kundenID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_USERS_SHOPPINGLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<ShoppingList> listOfShoppingLists = new ArrayList<ShoppingList>();
                            // fetch the Shoppinglist data from JSON

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int listID = jsonObject.getInt(Constants.REQ_RETURN_SHOPPINGLISTID);
                                String datum = jsonObject.getString(Constants.REQ_RETURN_SHOPPINGLIST_DATUM);
                                String supermarkt = jsonObject.getString(Constants.REQ_RETURN_SHOPPINGLIST_SUPERMARKT);
                                String status = jsonObject.getString(Constants.REQ_RETURN_SHOPPINGLIST_STATUS);

                                // convert the datum to a LocalDate Object
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate date = LocalDate.parse(datum, formatter);

                                // generate ListObject from Data
                                ShoppingList tempList = new ShoppingList(listID, date, supermarkt, status);

                                // add to the ShoppingList ArrayList
                                listOfShoppingLists.add(tempList);
                            }

                            // in der ListView anzeigen
                            adapter = new ShoppingListAdapter(getContext(), R.layout.listadapter_shoppinglist, listOfShoppingLists);
                            listView.setAdapter(adapter);

                        }catch(JSONException e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.REQ_PARAM_USERID, Integer.toString(kundenID));
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
