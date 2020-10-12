package com.example.hwr_huschka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hwr_huschka.klassen.Product;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProductNumberAdapter extends ArrayAdapter<Product> {

    private static final String TAG = "ProductAdapter";
    private Context context;
    int resource;

    public ProductNumberAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
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

        TextView cbProductName = (TextView) convertView.findViewById(R.id.TV_productName);
        TextView tvProductPrice = (TextView) convertView.findViewById(R.id.TV_listadapter_Preis);
        Spinner spinnerNumber = (Spinner) convertView.findViewById(R.id.spinner_ProduktAnzahl);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1,
                convertView.getResources().getStringArray(R.array.Anzahl));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNumber.setAdapter(myAdapter);
        // ImageView imageProduct = (ImageView) convertView.findViewById(R.id.productImage);


        cbProductName.setText(name);
        tvProductPrice.setText(Double.toString(preis));


        return convertView;
    }


}
