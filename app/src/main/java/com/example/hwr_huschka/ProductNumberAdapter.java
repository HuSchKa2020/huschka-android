package com.example.hwr_huschka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hwr_huschka.klassen.Product;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.recyclerview.widget.RecyclerView;


public class ProductNumberAdapter extends BaseAdapter {

    private HashMap<Product, Integer> mapData = new HashMap<Product, Integer>();
    private Product[] mKeys;
    private Context context;

    public ProductNumberAdapter(Context context, HashMap<Product, Integer> mapData){
        this.context = context;
        this.mapData  = mapData;
        mKeys = mapData.keySet().toArray(new Product[mapData.size()]);
    }

    public HashMap<Product, Integer> getProductsOfList(){
        return mapData;
    }

    @Override
    public int getCount() {
        return mapData.size();
    }

    @Override
    public Object getItem(int i) {
        return mapData.get(mKeys[i]);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater;
            inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.listadapter_product_spinner, null);
        }

        final Product key = mKeys[i];
        Integer value = mapData.get(key);

        TextView tv_prodName = (TextView) view.findViewById(R.id.TV_adapter_productName);
        TextView tv_prodPrice = (TextView) view.findViewById(R.id.TV_adapter_productPreis);
        final Spinner spinnerNumberOfProd = view.findViewById(R.id.spinner_ProduktAnzahl);

        Integer[] spinnerItems = new Integer[]{1,2,3,4,5,6,7,8,9};
        ArrayAdapter<Integer> myAdapter = new ArrayAdapter<Integer>(context,
                android.R.layout.simple_list_item_1,
                spinnerItems);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNumberOfProd.setAdapter(myAdapter);

        spinnerNumberOfProd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mapData.put(key, (Integer)spinnerNumberOfProd.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // setValues
        if (key!= null){
            tv_prodName.setText(key.getName());
            tv_prodPrice.setText(Double.toString(key.getPreis()));
        }


        int spinnerPosition = myAdapter.getPosition(value); //value is the Number of Products
        spinnerNumberOfProd.setSelection(spinnerPosition);


        return view;
    }
}
