package com.example.hwr_huschka.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.Product;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProductAdapter extends ArrayAdapter<Product> {

    private static final String TAG = "ProductAdapter";
    private Context context;
    int resource;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // get Values of the Produkt, that have to be shown in the Listview
        int produktID = getItem(position).getProduktID();
        String name = getItem(position).getName();
        double preis = getItem(position).getPreis();

        Product product = new Product(produktID, name, preis);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvProductName = (TextView) convertView.findViewById(R.id.TVproduktName);
        TextView tvProductPrice = (TextView) convertView.findViewById(R.id.TVproduktPrice);
        // ImageView imageProduct = (ImageView) convertView.findViewById(R.id.productImage);


        tvProductName.setText(name);
        tvProductPrice.setText(Double.toString(preis));


        return convertView;
    }

}
