package com.example.meetingnotesorganizer.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.meetingnotesorganizer.R;

public class ConfirmationDialogFragment extends DialogFragment {
    private boolean isPositiveClicked;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isPositiveClicked = true;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isPositiveClicked = false;
                    }
                });
        // Create the AlertDialog object and return it.
        return builder.create();
    }

    public boolean isPositiveClicked() {
        return isPositiveClicked;
    }
}
