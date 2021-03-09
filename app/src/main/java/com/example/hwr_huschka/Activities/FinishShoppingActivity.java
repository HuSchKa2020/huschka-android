package com.example.hwr_huschka.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hwr_huschka.ListAdapter.ProductCheckboxSpinnerPositionAdapter;
import com.example.hwr_huschka.R;

public class FinishShoppingActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btn_kasse, btn_qrcode;
    TextView tv_endpreis;
    ProductCheckboxSpinnerPositionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finisch_shopping);

        toolbar = findViewById(R.id.toolbar);
        btn_kasse = findViewById(R.id.btn_Kasse_zahlen);
        btn_qrcode = findViewById(R.id.btn_QR_Code);
        tv_endpreis = findViewById(R.id.TV_Endpreis);

        tv_endpreis.setText(Double.toString(Math.round(getIntent().getDoubleExtra("preis", 0.0) * 100)/100.0));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_kasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        btn_qrcode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QRCodeActivity.class);
                intent.putExtra("ListenID", getIntent().getIntExtra("ListenID", 0));
                startActivity(intent);
            }
        });

    }

    public void openDialog() {
        DialogFragment dialogFragment = new DialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case (android.R.id.home):
                finish();
                break;
        }

        return true;
    }
}