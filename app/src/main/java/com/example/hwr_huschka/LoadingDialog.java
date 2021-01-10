package com.example.hwr_huschka;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.view.LayoutInflater;


/**
 * Klasse zum Starten des eigenen Lade-Dialogs
 */
public class LoadingDialog {

    private Activity activity;
    private AlertDialog alertDialog;
    private int closeAfter = 200; // in Millisekunden

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingAnimation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));

        builder.setCancelable(true);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog() {
        // kurz Warten, falls dialog zu schnell geschlossen wird. Sieht sonst unschön aus.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                alertDialog.dismiss();
            }
        }, closeAfter);
    }
}
