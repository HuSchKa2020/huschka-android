package com.example.hwr_huschka.Activities;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hwr_huschka.Fragments.Login.LoginFragment;
import com.example.hwr_huschka.R;

public class ChangePasswordActivity extends AppCompatActivity {

    Button btnPasswortanzeigen;
    EditText edPassword1, edPassword2, edPassword3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        edPassword1 = findViewById(R.id.Passwort_Alt);
        edPassword2 = findViewById(R.id.Passwort_Neu);
        edPassword3 = findViewById(R.id.Passwort_Neu2);


        // show the Password
        btnPasswortanzeigen = findViewById(R.id.BTN_PasswortChange_anzeigen);
        btnPasswortanzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnPasswortanzeigen.getText().toString().equals("Anzeigen")) {
                    edPassword1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edPassword3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnPasswortanzeigen.setText("Verbergen");
                } else {
                    edPassword1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edPassword3.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnPasswortanzeigen.setText("Anzeigen");
                }

            }
        });

    }
}