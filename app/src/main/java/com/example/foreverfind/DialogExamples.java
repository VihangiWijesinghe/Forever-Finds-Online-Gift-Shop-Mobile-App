package com.example.foreverfind;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.fragment.app.DialogFragment;

import com.example.foreverfind.sessions.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DialogExamples extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Clear History")
                .setMessage("Clears all your order history").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SessionManagement sm= new SessionManagement(getActivity());
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
                String nowdate = sd.format(Calendar.getInstance().getTime());
                sm.orderPref(nowdate);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        return builder.create();
    }


}
