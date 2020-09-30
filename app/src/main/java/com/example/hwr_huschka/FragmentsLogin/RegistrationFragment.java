package com.example.hwr_huschka.FragmentsLogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hwr_huschka.Activities.MainActivity;
import com.example.hwr_huschka.Constants;
import com.example.hwr_huschka.FragmentsLogin.LoginFragment;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegistrationFragment extends Fragment {

    TextView goToLogin;

    Button btnRegister;
    Button btnPasswortanzeigen;


    EditText edEmail, edPassword1, edPassword2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_registration, container, false);

        goToLogin = v.findViewById(R.id.TVtoLogin);
        btnRegister = v.findViewById(R.id.btn_register);
        edEmail = v.findViewById(R.id.register_EmailField);

        edPassword1= v.findViewById(R.id.register_PasswordField);
        edPassword2= v.findViewById(R.id.register_PasswordField2);




        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentLoginContainer, new LoginFragment(), "login");
                ft.commit();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String firstname = edFirstname.getText().toString().trim();
                String familyname = edFamilyname.getText().toString().trim();

                registerUser(email, password, firstname, familyname);
            }
        });

        btnPasswortanzeigen = v.findViewById(R.id.btn_register_Passwort_anzeigen);
        btnPasswortanzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnPasswortanzeigen.getText().toString().equals("Anzeigen")) {
                    edPassword1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnPasswortanzeigen.setText("Verbergen");
                } else {
                    edPassword1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnPasswortanzeigen.setText("Anzeigen");
                }
            }

        });

        return v;
    }

    private void openMainActivity(){
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void registerUser(final String email, final String password, final String firstname, final String familyname){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // was einem das PHP Skript zurückgibt wird hier behandelt
                        // jsonObject, dass was in Postman zurückgegeben wurde
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("error") == false){
                                openMainActivity();
                            } else if (jsonObject.getString("message").equals("User already registered")){
                                Toast.makeText(getContext(), "Sie sind schon registriert, bitte loggen sie sich ein!", Toast.LENGTH_LONG).show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("vorname", firstname);
                params.put("nachname", familyname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
