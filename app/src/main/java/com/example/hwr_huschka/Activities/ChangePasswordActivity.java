package com.example.hwr_huschka.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hwr_huschka.DatabaseHelper;
import com.example.hwr_huschka.Fragments.Login.LoginFragment;
import com.example.hwr_huschka.R;

public class ChangePasswordActivity extends AppCompatActivity {

    Button btnPasswortanzeigen, btn_Abbrechen, btn_Speichern;
    EditText edPasswordAlt, edPasswordNeu, edPasswordNeu2;

    SharedPreferences sharedPreferences;
    int userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        sharedPreferences = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt("id", 0);

        edPasswordAlt = findViewById(R.id.Passwort_Alt);
        edPasswordNeu = findViewById(R.id.Passwort_Neu);
        edPasswordNeu2 = findViewById(R.id.Passwort_Neu2);
        btnPasswortanzeigen = findViewById(R.id.BTN_PasswortChange_anzeigen);
        btn_Abbrechen = findViewById(R.id.BTN_Passswort_Abbrechen);
        btn_Speichern = findViewById(R.id.BTN_Password_Speichern);


        btnPasswortanzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnPasswortanzeigen.getText().toString().equals("Anzeigen")) {
                    edPasswordAlt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edPasswordNeu.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edPasswordNeu2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnPasswortanzeigen.setText("Verbergen");
                } else {
                    edPasswordAlt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edPasswordNeu.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edPasswordNeu2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnPasswortanzeigen.setText("Anzeigen");
                }

            }
        });

        btn_Abbrechen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Speichern.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String passwordNeu, passwordNeu2, passwordAlt;

                passwordAlt = edPasswordAlt.getText().toString();
                passwordNeu = edPasswordNeu.getText().toString();
                passwordNeu2 = edPasswordNeu2.getText().toString();

                if (passwordNeu.equals(passwordNeu2) && passwordNeu.length() > 6)  {
                    changePassword(ChangePasswordActivity.this, userID, passwordAlt, passwordNeu);
                } else {
                    Toast.makeText(getApplicationContext(),"Entweder ist Ihr Passwort zu kurz oder die Passwörter nicht gleich.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void changePassword(Activity activity, int userID, String passwort_alt, String passwort_neu) {
        DatabaseHelper.changePassword(activity, userID,passwort_alt, passwort_neu);
    }
}
