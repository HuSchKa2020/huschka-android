package com.example.hwr_huschka.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hwr_huschka.Constants;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AccountActivity extends AppCompatActivity {

    TextView tv_KontoVorname, tv_KontoNachname, tv_KontoPostleitzahl, tv_KontoStadt, tv_KontoStraße, tv_KontoHausnummer, tv_KontoEmail;

    Button btn_Passwort_aendern, btn_Kontodaten_aendern;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tv_KontoVorname = findViewById(R.id.Konto_Vorname);
        tv_KontoNachname = findViewById(R.id.Konto_Nachname);
        tv_KontoPostleitzahl = findViewById(R.id.Konto_Postleitzahl);
        tv_KontoStadt = findViewById(R.id.Konto_Stadt);
        tv_KontoStraße = findViewById(R.id.Konto_Straße);
        tv_KontoHausnummer = findViewById(R.id.Konto_Hausnummer);
        tv_KontoEmail = findViewById(R.id.Konto_Email);
        btn_Passwort_aendern = findViewById(R.id.BTN_Passwort_Aendern);
        btn_Kontodaten_aendern = findViewById(R.id.BTN_Daten_Aendern);
        toolbar = findViewById(R.id.toolbar);

        sharedPreferences = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);

        loadUserdata(getApplicationContext(), sharedPreferences.getInt("id", 0));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_Passwort_aendern.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        btn_Kontodaten_aendern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangeAccountDataActivity.class);
                startActivity(intent);
            }
        });

    }



    /**
     * Search Product by a Part of a Productname
     */
    private void loadUserdata(final Context context, final int userID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOAD_USER_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println(response);
                            tv_KontoVorname.setText(jsonObject.getString(Constants.REQ_RETURN_USER_FIRSTNAME));
                            tv_KontoNachname.setText(jsonObject.getString(Constants.REQ_RETURN_USER_FAMILYNAME));
                            tv_KontoEmail.setText(jsonObject.getString(Constants.REQ_RETURN_USER_MAIL));
                            /*Adresse
                            tv_KontoStraße.setText(jsonObject.getString(Constants.REQ_RETURN_USER_FIRSTNAME));

                            tv_KontoHausnummer.setText(jsonObject.getString(Constants.REQ_RETURN_USER_FIRSTNAME));
                            tv_KontoPostleitzahl.setText(jsonObject.getString(Constants.REQ_RETURN_USER_FIRSTNAME));
                            tv_KontoStadt.setText(jsonObject.getString(Constants.REQ_RETURN_USER_FIRSTNAME));
                            */

                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.REQ_PARAM_USERID, Integer.toString(userID));
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
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


