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
import com.example.hwr_huschka.Activities.ShoppinglistActivity;
import com.example.hwr_huschka.Constants;
import com.example.hwr_huschka.ProductAdapter;
import com.example.hwr_huschka.Activities.ProductInfoActivity;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.ShoppingListAdapter;
import com.example.hwr_huschka.klassen.Product;
import com.example.hwr_huschka.klassen.ShoppingList;

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

public class ProductFragment extends Fragment {

    EditText searchedProductName;
    Button btnSearch;
    ListView productListView;

    ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_produkte, container, false);

        searchedProductName = v.findViewById(R.id.edTextProduct);
        btnSearch = v.findViewById(R.id.btnProductSearch);
        productListView = v.findViewById(R.id.produktListView);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProducts(searchedProductName.getText().toString());
            }
        });

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ProductInfoActivity.class);
                Product clickedItem = (Product) adapterView.getItemAtPosition(i);
                //Toast.makeText(getContext(), Integer.toString(clickedItem.getProduktID()), Toast.LENGTH_LONG).show();

                intent.putExtra("productID", Integer.toString(clickedItem.getProduktID()));
                startActivity(intent);
            }
        });

        return v;
    }

    private void loadProducts(final String partOfProductName) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<Product> productArrayList = new ArrayList<Product>();

                            // fetch the Product data from JSON
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                
                                int produktID = jsonObject.getInt("ProduktID");
                                String hersteller = jsonObject.getString("Hersteller");
                                String name = jsonObject.getString("Name");
                                String kategorie = jsonObject.getString("Kategorie");
                                double preis = jsonObject.getDouble("Preis");
                                int kcal = jsonObject.getInt("Kcal");

                                // generate Product Object from Data
                                Product temp = new Product(produktID, hersteller, name, kategorie, preis, kcal);

                                // add to the Product ArrayList
                                productArrayList.add(temp);
                            }

                            // in der ListView anzeigen
                            adapter = new ProductAdapter(getContext(), R.layout.listadapter_product, productArrayList);
                            productListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Name", partOfProductName);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}


