package com.example.crowderapp.views;

import androidx.fragment.app.DialogFragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowderapp.R;
import com.example.crowderapp.ScanActivity;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.ScanObjHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.controllers.callbackInterfaces.ScanObjectCallback;
import com.example.crowderapp.controllers.callbackInterfaces.createExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getUserByIDCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.ScanObj;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.trialfragments.BinomialTrialFragment;
import com.example.crowderapp.views.trialfragments.TrialFragment;
/**
 * Allows user to enter a non negative integer they want to be associated to a barcode
 */
public class NonNegBarcodeFragment extends DialogFragment {

    EditText integerEditText;
    private Experiment experiment;
    private OnFragmentInteractionListener listener;
    private ScanObjHandler soHandler;

    public interface OnFragmentInteractionListener {
        void onOkPressed();
    }


    public static NonNegBarcodeFragment newInstance(Experiment experiment) {
        NonNegBarcodeFragment frag = new NonNegBarcodeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Experiment", experiment);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);

        experiment = (Experiment) getArguments().get("Experiment");
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.non_neg_barcode_fragment, null);

        integerEditText = view.findViewById(R.id.non_neg_EditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Set Integer Count")
                .setIcon(R.drawable.baseline_science_24)
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
        if(ad == null) {
            return;
        }
        Button posButton = ad.getButton(Dialog.BUTTON_POSITIVE);
        posButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newIntegerVal = integerEditText.getText().toString();


                if(newIntegerVal.equals("")) {
                    Context context = getContext();
                    CharSequence text = "No Option Selected";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    // Start the scanner
                    Intent intent = new Intent(getActivity(), ScanActivity.class);
                    startActivityForResult(intent, 0);

                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get data from scanner activity

        if(data == null)
            return;

        String code = data.getStringExtra("CODE");
        Log.v("Barcode Frag", code);
        String newIntegerVal = integerEditText.getText().toString();

        // Create scan object based on retrieved values
        soHandler = new ScanObjHandler(experiment.getExperimentID());
        soHandler.createScanObj(code, newIntegerVal, new ScanObjectCallback() {
            @Override
            public void callback(ScanObj o) {
                AlertDialog ad = (AlertDialog) getDialog();
                ad.dismiss();
            }
        });

    }

}
