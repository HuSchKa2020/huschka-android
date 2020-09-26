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

import com.example.hwr_huschka.Activities.AddShoppingListActivity;
import com.example.hwr_huschka.ProductAdapter;
import com.example.hwr_huschka.ProductInfoActivity;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.ShoppingListAdapter;
import com.example.hwr_huschka.klassen.Product;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProductFragment extends Fragment {

    EditText searchedProductName;
    Button btnSearch;
    ListView productListView;

    ArrayList<Product> products;
    ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_produkte, container, false);

        searchedProductName = v.findViewById(R.id.edTextProduct);
        btnSearch = v.findViewById(R.id.btnProductSearch);
        productListView = v.findViewById(R.id.produktListView);

        products = new ArrayList<Product>();
        products.add(new Product(1,"Ketchup", 1.99));
        products.add(new Product(2, "Mayo", 1.49));

        adapter = new ProductAdapter(getContext(), R.layout.listadapter_product, products);
        productListView.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Suchen in der DB nach dem Produkt (ggfs. alle Produkte, die das was im Textfeld steht enthalten, in die ListView schreiben)
                products.clear();
                products.addAll(loadProducts(searchedProductName.getText().toString()));

                products.add(new Product(38,"Reis", 0.99));
                adapter.notifyDataSetChanged();
            }
        });

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), ProductInfoActivity.class));
            }
        });

        return v;
    }

    private ArrayList<Product> loadProducts(String partOfProductName){
        // loadProducts from Backend
        return new ArrayList<Product>();
    }

}
