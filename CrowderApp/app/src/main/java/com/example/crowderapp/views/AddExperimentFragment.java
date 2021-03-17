package com.example.crowderapp.views;

import androidx.fragment.app.DialogFragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowderapp.R;

public class AddExperimentFragment extends DialogFragment {

    private static final String[] options = new String[]{
            "Select Trial Type", "Count", "Binary", "Non-Negative Integer", "Measurement"};

    private Spinner dropdown;
    private EditText minTrialsEditText;
    private EditText experimentNameEditText;

    private OnFragmentInteractionListener listener;
    private int optionSelection;

    public interface OnFragmentInteractionListener {
        void onOkPressed();
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_experiment_fragment_layout, null);

        minTrialsEditText = view.findViewById(R.id.min_trials_EditText);
        experimentNameEditText = view.findViewById(R.id.experiment_name_EditText);

        dropdown = view.findViewById(R.id.dropdown);
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
                .setTitle("Add Experiment")
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
                if(dropdown.getSelectedItem().toString() == options[0] ||
                        minTrialsEditText.getText().toString().matches("") ||
                        experimentNameEditText.getText().toString().matches("")) {
                    Context context = getContext();
                    CharSequence text = "Missing Fields";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    ad.dismiss();
                }
            }
        });

    }




}
