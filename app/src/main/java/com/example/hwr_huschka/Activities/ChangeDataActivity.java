package com.example.hwr_huschka.Activities;

import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hwr_huschka.R;

public class ChangeDataActivity extends AppCompatActivity {

    Button btn_Abbrechen, btn_Speichern;
    EditText edVorname, edNachname, edPLZ, edStadt, edStraße, edNummer, edEmail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);

        edVorname = findViewById(R.id.Edit_Vorname);
        edNachname = findViewById(R.id.Edit_Nachname);
        edPLZ = findViewById(R.id.Edit_Postleitzahl);
        edStadt = findViewById(R.id.Edit_Stadt);
        edStraße = findViewById(R.id.Edit_Straße);
        edNummer = findViewById(R.id.Edit_Hausnummer);
        edEmail = findViewById(R.id.Edit_Email);
        btn_Abbrechen = findViewById(R.id.BTN_Passswort_Abbrechen);
        btn_Speichern = findViewById(R.id.BTN_Password_Speichern);


        btn_Abbrechen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Speichern.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String vorname, nachname, stadt,postleitzahl, straße, nummer, email;

                vorname = edVorname.getText().toString();
                nachname = edNachname.getText().toString();
                stadt = edStadt.getText().toString();
                straße = edStraße.getText().toString();
                email = edEmail.getText().toString();
                postleitzahl = edPLZ.getText().toString();
                nummer = edNummer.getText().toString();


                    //weiß nicht ob man das eleganter machen könnte, aber es funktioniert

                if (vorname.length() > 0 && nachname.length() > 0 && stadt.length() > 0 && straße.length() > 0 && email.length() > 0 && postleitzahl.length() > 0 && nummer.length() > 0 && email.contains("@")){
                    changeData(getApplicationContext(), vorname, nachname, postleitzahl, stadt, straße, nummer, email);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Sie haben nicht alle Felder ausgefüllt.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    // Habe hier alle Strings gemacht, weil ich oben ja nicht getText().toInteger machen kann, sondern nur .toString()

    private void changeData(Context context, String vorname, String nachname, String postleitzahl, String stadt, String straße, String nummer, String email ) {

    }
}

