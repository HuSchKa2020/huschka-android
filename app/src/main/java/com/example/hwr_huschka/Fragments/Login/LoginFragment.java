package com.example.hwr_huschka.Fragments.Login;


import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.hwr_huschka.Activities.MainActivity;
import com.example.hwr_huschka.Constants;
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

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    TextView goToRegistration;
    Button btnLogin;
    Button btnPasswortAnzeigen;

    EditText edEmail, edPassword;

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreferences = this.getActivity().getSharedPreferences("userdata", MODE_PRIVATE);
        if(sharedPreferences.contains("email")){
            openMainActivity();
        }
        View v = inflater.inflate(R.layout.fragment_login_login, container, false);

        edEmail = v.findViewById(R.id.login_EmailField);
        edPassword = v.findViewById(R.id.login_PasswordField);

        // go to the Registration if the User have no Account
        goToRegistration = v.findViewById(R.id.TVtoRegistration);
        goToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentLoginContainer, new RegistrationFragment(), "register");
                ft.commit();
            }
        });

        // login the User
        btnLogin = v.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if (!email.equals("") && email.contains("@")
                    && !password.equals("")){ // logins fields are okay
                    logInUser(email, password);
                } else{
                    // email and password field are not correct
                    Toast.makeText(getContext(), "Bitte füllen Sie das Feld Password und Email korrekt aus!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // show the Password if the User clicks the Button
        btnPasswortAnzeigen = v.findViewById(R.id.btn_login_Passwort_anzeigen);
        btnPasswortAnzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnPasswortAnzeigen.getText().toString().equals("Anzeigen")) {
                    edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnPasswortAnzeigen.setText("Verbergen");
                } else {
                    edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnPasswortAnzeigen.setText("Anzeigen");
                }
            }
        });

        return v;

    }

    /**
     * This Method opens the Main Activity
     */
    private void openMainActivity(){
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
    }

    /**
     * This Method is for logging in the User.
     * @param email Mail of the User
     * @param password Password of the User
     */
    private void logInUser(final String email, final String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("error") == false){
                                // mail and password are correct
                                // safe user data in SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putInt("id", jsonObject.getInt(Constants.REQ_RETURN_USERID));
                                editor.putString("email", jsonObject.getString(Constants.REQ_RETURN_USER_MAIL));
                                editor.putString("firstname", jsonObject.getString(Constants.REQ_RETURN_USER_FIRSTNAME));
                                editor.putString("familyname", jsonObject.getString(Constants.REQ_RETURN_USER_FAMILYNAME));
                                editor.commit();

                                openMainActivity();
                            } else{
                                // mail and password are not correct
                                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                params.put(Constants.REQ_PARAM_USER_MAIL, email);
                params.put(Constants.REQ_PARAM_USER_PASSWORD, password);
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
