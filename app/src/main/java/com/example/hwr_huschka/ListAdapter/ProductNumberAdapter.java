package com.example.hwr_huschka.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hwr_huschka.R;
import com.example.hwr_huschka.klassen.Product;

import java.util.HashMap;


public class ProductNumberAdapter extends BaseAdapter {

    private HashMap<Product, Integer> mapData = new HashMap<Product, Integer>();
    private Product[] mKeys;
    private Context context;

    private boolean showSpinner = false;

    public ProductNumberAdapter(Context context, HashMap<Product, Integer> mapData) {
        this.context = context;
        this.mapData = mapData;
        mKeys = mapData.keySet().toArray(new Product[mapData.size()]);
    }

    public ProductNumberAdapter(Context context, HashMap<Product, Integer> mapData, boolean showSpinner) {
        this.context = context;
        this.mapData = mapData;
        mKeys = mapData.keySet().toArray(new Product[mapData.size()]);

        this.showSpinner = showSpinner;
    }

    public HashMap<Product, Integer> getProductsOfList() {
        return mapData;
    }

    @Override
    public int getCount() {
        return mapData.size();
    }

    @Override
    public Object getItem(int i) {
        return mapData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater;
            inflater = LayoutInflater.from(context);
            if (showSpinner) {
                view = inflater.inflate(R.layout.listadapter_product_spinner, null);
            } else {
                view = inflater.inflate(R.layout.listadapter_product_number, null);
            }

        }

        final Product key = mKeys[i];
        Integer value = mapData.get(key);

        TextView tv_prodName = (TextView) view.findViewById(R.id.TV_adapter_productName);
        TextView tv_prodPrice = (TextView) view.findViewById(R.id.TV_adapter_productPreis);

        if (showSpinner == true) {
            final Spinner spinnerNumberOfProd = view.findViewById(R.id.spinner_ProduktAnzahl);
            // set Items to the Spinner
            Integer[] spinnerItems = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            ArrayAdapter<Integer> myAdapter = new ArrayAdapter<Integer>(context,
                    android.R.layout.simple_list_item_1,
                    spinnerItems);
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerNumberOfProd.setAdapter(myAdapter);

            spinnerNumberOfProd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mapData.put(key, (Integer) spinnerNumberOfProd.getSelectedItem());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            int spinnerPosition = myAdapter.getPosition(value); //value is the Number of Products
            spinnerNumberOfProd.setSelection(spinnerPosition);

        } else { // show Numberfield
            TextView numberOf = view.findViewById(R.id.TV_adapter_ProduktAnzahl);
            numberOf.setText(Integer.toString(value));
        }

        // setValues
        if (key != null) {
            tv_prodName.setText(key.getName());
            tv_prodPrice.setText(Double.toString(key.getPreis()));
        }





        return view;
    }
}
