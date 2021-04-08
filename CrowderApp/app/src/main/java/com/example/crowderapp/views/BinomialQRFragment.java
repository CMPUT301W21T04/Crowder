package com.example.crowderapp.views;

import androidx.fragment.app.DialogFragment;


import android.app.AlertDialog;
import android.app.Dialog;
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
import androidx.fragment.app.Fragment;

import com.example.crowderapp.QRCodeActivity;
import com.example.crowderapp.R;
import com.example.crowderapp.ScanActivity;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.controllers.callbackInterfaces.createExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getUserByIDCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.trialfragments.BinomialTrialFragment;
import com.example.crowderapp.views.trialfragments.TrialFragment;

public class BinomialQRFragment extends DialogFragment {

    private static final String[] options = new String[]{
            "Select Trial Type", "Pass", "Fail"};

    private Spinner dropdown;
    private OnFragmentInteractionListener listener;
    private Experiment experiment;

    public interface OnFragmentInteractionListener {
        void onOkPressed();
    }

    public static BinomialQRFragment newInstance(Experiment experiment) {
        BinomialQRFragment frag = new BinomialQRFragment();
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.binomial_barcode_fragment, null);


        dropdown = view.findViewById(R.id.dropdown_binomial);
        // https://stackoverflow.com/questions/40339499/how-to-create-an-unselectable-hint-text-for-spinner-in-android-without-reflec

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, options) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = null;

                // If this is the initial dummy entry, make it hidden
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                } else {
                    v = super.getDropDownView(position, null, parent);
                }

                return v;
            }
        };
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Set Type")
                .setIcon(R.drawable.baseline_science_24)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("Selection", dropdown.getSelectedItem().toString());
                    }
                }).create();
    }
    // The use of onResume to be able to override the automatic dismiss was learned from
    // StackOverflow, https://stackoverflow.com/
    // an answer by Sogger on Mar 25 '13 at 15:48
    // https://stackoverflow.com/users/579234/sogger
    // to question "How to prevent a dialog from closing when a button is clicked"
    // https://stackoverflow.com/questions/2620444/how-to-prevent-a-dialog-from-closing-when-a-button-is-clicked
    // under CC-BY-SA
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
                String binomialAction = dropdown.getSelectedItem().toString();


                if(binomialAction == options[0]) {
                    Context context = getContext();
                    CharSequence text = "No Option Selected";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    Intent intent = new Intent(getActivity(), QRCodeActivity.class);
                    intent.putExtra("Experiment", experiment);
                    intent.putExtra("Value", binomialAction);
                    startActivity(intent);
                    ad.dismiss();
                }
            }
        });

    }


}
