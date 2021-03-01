package com.example.hwr_huschka.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class UeberpruefenFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Einkauf überprüfen")
                .setMessage("Bitte vorher Einkauf überprüfen und dann kannst du fort fahren, wie Mustang Besitzer.")
                .setPositiveButton("Zum Bezahlen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(), FinishShoppingActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }
}
