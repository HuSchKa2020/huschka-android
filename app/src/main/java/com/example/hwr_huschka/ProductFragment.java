package com.example.hwr_huschka;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

        searchedProductName = v.findViewById(R.id.edTextProduct);
        btnSearch = v.findViewById(R.id.btnProductSearch);
        productListView = v.findViewById(R.id.produktListView);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Suchen in der DB nach dem Produkt (ggfs. alle Produkte, die das was im Textfeld steht enthalten, in die ListView schreiben)
            }
        });

        return v;
    }

}
