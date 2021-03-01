package com.example.hwr_huschka.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hwr_huschka.R;

public class QRCodeActivity extends AppCompatActivity {


    Button btn_qr_beenden;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        btn_qr_beenden = findViewById(R.id.BTN_QR_Code_beenden);

        btn_qr_beenden.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMain);
            }
        });


    }
}