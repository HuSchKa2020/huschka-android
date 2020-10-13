package com.example.hwr_huschka.FragmentsMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hwr_huschka.Constants;
import com.example.hwr_huschka.DatabaseHelper;
import com.example.hwr_huschka.ListAdapter.ProductAdapter;
import com.example.hwr_huschka.Activities.ProductInfoActivity;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.RequestHandler;
import com.example.hwr_huschka.klassen.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProductFragment extends Fragment {

    EditText searchedProductName;
    Button btnSearch;
    ListView productListView;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_produkte, container, false);

        // EditText
        searchedProductName = v.findViewById(R.id.edTextProduct);
        // Button
        btnSearch = v.findViewById(R.id.btnProductSearch);
        // ListView
        productListView = v.findViewById(R.id.produktListView);

        // show all available Products
        DatabaseHelper.searchProduct(getContext(), "", productListView);

        // search for Products
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper.searchProduct(getContext(), searchedProductName.getText().toString(), productListView);
            }
        });

        // on Click a Product
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ProductInfoActivity.class);
                Product clickedItem = (Product) adapterView.getItemAtPosition(i);
                intent.putExtra("productID", Integer.toString(clickedItem.getProduktID()));
                startActivity(intent);
            }
        });

        return v;
    }
}


