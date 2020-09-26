package com.example.hwr_huschka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hwr_huschka.klassen.ShoppingList;

import org.threeten.bp.LocalDate;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShoppingListAdapter extends ArrayAdapter<ShoppingList> {

    private static final String TAG = "ShoopingListAdapter";
    private Context context;
    int resource;

    public ShoppingListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ShoppingList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LocalDate date = getItem(position).getDatum();
        String supermarkt = getItem(position).getSupermarkt();
        String status = getItem(position).getStatus();

        ShoppingList shoppingList = new ShoppingList(date, supermarkt, status);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvSupermarket = (TextView) convertView.findViewById(R.id.TVsupermarketShoppingList);
        TextView tvdatum = (TextView) convertView.findViewById(R.id.TVdatumShoppingList);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.TVstatusShoppingList);

        tvSupermarket.setText(supermarkt);
        tvStatus.setText(status);
        tvdatum.setText(date.toString());

        return convertView;
    }
}
