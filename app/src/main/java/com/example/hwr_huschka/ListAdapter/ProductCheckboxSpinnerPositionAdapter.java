package com.example.hwr_huschka.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.Position;
import com.example.hwr_huschka.klassen.Product;
import com.example.hwr_huschka.klassen.ProductInShoppinglist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Listadapter für die listadapter_position_spinner_checkbox.xml
 */
public class ProductCheckboxSpinnerPositionAdapter extends BaseAdapter {
    private ProductInShoppinglist[] mProducts;
    private Context context;

    public ProductCheckboxSpinnerPositionAdapter(Context context, ProductInShoppinglist[] products) {
        this.context = context;
        this.mProducts = products;

        System.out.println("hier");
    }

    public ArrayList<ProductInShoppinglist> getCheckedProducts() {
        ArrayList<ProductInShoppinglist> products = new ArrayList<ProductInShoppinglist>(); // alle Produkte die in der Liste "gecheckt wurden" 
        for (ProductInShoppinglist product: mProducts) {
            if (product.isAusgewaehlt() == true) { // Produkt wurde ausgwählt
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public int getCount() {
        return mProducts.length;
    }

    @Override
    public Object getItem(int i) {
        return mProducts[i];
    }

    @Override
    public long getItemId(int i) {
        return mProducts[i].getProduktID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.listadapter_position_spinner_checkbox, null);


        final ProductInShoppinglist product = mProducts[i];
        if (product != null) {
            Boolean isChecked = product.isAusgewaehlt();
            TextView tv_name = (TextView) view.findViewById(R.id.TV_adapter_productName);
            TextView tv_reihe = (TextView) view.findViewById(R.id.TV_adapter_productReihe);
            TextView tv_regal = (TextView) view.findViewById(R.id.TV_adapter_productRegal);

            tv_name.setText(product.getName());
            tv_reihe.setText("Reihe: " + Integer.toString(product.getPosition().getReihe()));
            tv_regal.setText("Reagalhöhe: " + Integer.toString(product.getPosition().getRegal()));

            // Spinner
            final Spinner spinnerNumberOfProd = view.findViewById(R.id.spinner_ProduktAnzahl);
            // set Items to the Spinner
            Integer[] spinnerItems = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            ArrayAdapter<Integer> myAdapter = new ArrayAdapter<Integer>(context,
                    android.R.layout.simple_list_item_1,
                    spinnerItems);
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerNumberOfProd.setAdapter(myAdapter);

            spinnerNumberOfProd.setSelection(product.getAnzahl());

            spinnerNumberOfProd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    product.setAnzahl((Integer) spinnerNumberOfProd.getSelectedItem());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            // Checkbox
            CheckBox checkBox = view.findViewById(R.id.checkbox_einkaufen);
            checkBox.setSelected(isChecked);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    product.setAusgewaehlt(!product.isAusgewaehlt());
                }
            });
        }
        return view;
    }
}
