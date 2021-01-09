package com.example.hwr_huschka.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.hwr_huschka.Fragments.Main.HomeFragment;
import com.example.hwr_huschka.Fragments.Main.ProductFragment;
import com.example.hwr_huschka.R;
import com.example.hwr_huschka.Fragments.Main.ShoppingListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Home Fragment auswählen
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                new HomeFragment()).commit();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navigationListener);
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    // switch between fragments in the Bottom Navigation
                    switch(item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            toolbar.setTitle(R.string.nameHomeScreen);
                            break;
                        case R.id.nav_Lists:
                            selectedFragment = new ShoppingListFragment();
                            toolbar.setTitle(R.string.ListenScreen);
                            break;
                        case R.id.nav_ProductSearch:
                            selectedFragment = new ProductFragment();
                            toolbar.setTitle(R.string.ProduktScreen);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            selectedFragment).commit();

                    return true;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.toolbar_dropdown_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // recognize a Click on the OnOptionsMenu in the right corner of the App
        switch(item.getItemId()){
            case R.id.Settings:
                Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intentSettings);
                return true;
            case R.id.impressum:
                Intent intentImpressum = new Intent(getApplicationContext(), ImpressumActivity.class);
                startActivity(intentImpressum);
                return true;
            case R.id.logout:
                clearSharedpreferences();
                goToLogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // es sollte eigentlich richtig zwischen den Fragments navigiert werden
        // implementierung höchstwahrscheinlich hier
    }

    private void clearSharedpreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("userdata", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.commit();
    }

    private void goToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}