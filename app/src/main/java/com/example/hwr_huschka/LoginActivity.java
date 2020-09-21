package com.example.hwr_huschka;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Home Fragment auswählen
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLoginContainer,
                new LoginFragment()).commit();

    }
}
