 package com.example.crowderapp.views;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.LocationHandler;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.posts.Question;
import com.example.crowderapp.models.posts.Reply;
import com.example.crowderapp.views.trialfragments.TrialFragment;

public class LocationPopupFragment extends DialogFragment {

    private TextView popupText;
    private Experiment currentExp;
    boolean allow = false;
    LocationHandler locHandler;


    private LocationPopupFragment.OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(boolean allow);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locHandler = new LocationHandler(context);
        if (context instanceof LocationPopupFragment.OnFragmentInteractionListener){
            listener = (LocationPopupFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public static LocationPopupFragment newInstance(Experiment exp) {
        Bundle args = new Bundle();
        args.putSerializable("Experiment", exp);
        LocationPopupFragment fragment = new LocationPopupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        currentExp = (Experiment) args.getSerializable("Experiment");

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.location_popup_fragment, null);
        popupText = view.findViewById(R.id.locationPopupText);

        if(currentExp.isLocationRequired()) {
            if(locHandler.hasGPSPermissions()) {
                popupText.setText("Using your current location for this experiment.");
                allow = true;
            }
            else {
                popupText.setText("Please allow location services to participate in this experiment.");
                allow = false;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Location Services")
                .setIcon(R.drawable.ic_baseline_mode_comment_24)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();

    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog ad = (AlertDialog) getDialog();
        if (ad == null) {
            return;
        }
        Button posButton = ad.getButton(Dialog.BUTTON_POSITIVE);
        posButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOkPressed(allow);
                ad.dismiss();
            }
        });
    }
}
