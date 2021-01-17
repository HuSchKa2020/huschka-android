package com.example.hwr_huschka.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hwr_huschka.Constants;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.RequestHandler;
import com.example.hwr_huschka.klassen.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ProductInfoActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvProductID, tvHersteller, tvProductName, tvProductKategorie, tvPrice, tvKcal, tvGS, tvUS, tvErnaehrungsform;
    Product product = new Product();

    InputStream is = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        toolbar = findViewById(R.id.addListToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvProductID = findViewById(R.id.TVproductID);
        tvHersteller = findViewById(R.id.TVproductHersteller);
        tvProductName = findViewById(R.id.TVproductName);
        tvProductKategorie = findViewById(R.id.TVproductKategorie);
        tvPrice = findViewById(R.id.TVproductPreis);
        tvKcal = findViewById(R.id.TVproductKcal);
        tvGS = findViewById(R.id.TVproductGesundheitsScore);
        tvUS = findViewById(R.id.TVproductUmweltScore);
        tvErnaehrungsform = findViewById(R.id.TVproductErnaehrungsform);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData(Integer.parseInt(getIntent().getStringExtra("productID")));
    }

    /**
     * This Method load the Data of the Product.
     * @param productID id of the Product.
     */
    private void loadData(final int productID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_PRODUCT_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println(response);
                            tvProductID.setText(jsonObject.getString(Constants.REQ_RETURN_PRODUKTID));
                            tvHersteller.setText(jsonObject.getString(Constants.REQ_RETURN_PRODUKT_PRODUCER));
                            tvProductName.setText(jsonObject.getString(Constants.REQ_RETURN_PRODUKT_NAME));
                            tvPrice.setText(jsonObject.getString(Constants.REQ_RETURN_PRODUKT_PRICE));
                            tvKcal.setText(jsonObject.getString(Constants.REQ_RETURN_PRODUKT_KCAL));
                            tvProductKategorie.setText(jsonObject.getString(Constants.REQ_RETURN_PRODUKT_KATEGORIE));
                            tvErnaehrungsform.setText(jsonObject.getString(Constants.REQ_RETURN_ERNAEHRUNGSFORM));
                            tvGS.setText(jsonObject.getString(Constants.REQ_RETURN_GESUNDHEITSSCORE));
                            tvUS.setText(jsonObject.getString(Constants.REQ_RETURN_UMWELTSCORE));

                        }catch(JSONException e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.REQ_RETURN_PRODUKTID, Integer.toString(productID));
                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case (android.R.id.home):
                finish();
                break;
        }

        return true;
    }
}