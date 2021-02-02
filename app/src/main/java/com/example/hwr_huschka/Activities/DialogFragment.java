package com.example.hwr_huschka.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;

import com.example.hwr_huschka.R;

public class DialogFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Vielen Dank")
                .setMessage("HuSchKa - Ihr Einkaufsplaner.")
                .setPositiveButton("Zurück zum Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intentMain = new Intent(getContext(), MainActivity.class);
                        startActivity(intentMain);
                    }
                });

        return builder.create();
    }
}
