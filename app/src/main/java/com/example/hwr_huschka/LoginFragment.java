package com.example.hwr_huschka;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment {

    TextView goToRegistration;
    Button btnLogin;

    EditText edEmail, edPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_login, container, false);

        edEmail = v.findViewById(R.id.login_EmailField);
        edPassword = v.findViewById(R.id.login_PasswordField);

        goToRegistration = v.findViewById(R.id.TVtoRegistration);
        goToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentLoginContainer, new RegistrationFragment(), "register");
                ft.commit();

                //Toast.makeText(getContext(), "You Clicke here", Toast.LENGTH_LONG).show();
            }
        });

        btnLogin = v.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // logInUser(edEmail.getText().toString().trim(), edPassword.getText().toString().trim());
                openMainActivity();
            }
        });

        return v;
    }

    private void openMainActivity(){
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void logInUser(final String email, final String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // was einem das PHP Skript zurückgibt wird hier behandelt
                        // jsonObject, dass was in Postman zurückgegeben wurde
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("login") == true) {
                                // User Daten korrekt
                                // ID in die sharedPreferences
                                openMainActivity();
                            } else {
                                // Eingegebene Daten nicht korrekt?
                                Toast.makeText(getContext(), "E-Mail oder Password falsch!", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
