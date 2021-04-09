package com.example.crowderapp.views.trialfragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crowderapp.MainActivity;
import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.LocationHandler;
import com.example.crowderapp.controllers.callbackInterfaces.LocationCallback;
import com.example.crowderapp.controllers.callbackInterfaces.addTrialCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.models.CounterTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Location;
import com.example.crowderapp.models.MeasurementExperiment;
import com.example.crowderapp.models.MeasurementTrial;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.AllExperimentsFragment;
import com.example.crowderapp.views.LocationPopupFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class MeasurementTrialFragment extends TrialFragment {
    MeasurementExperiment measurementExperiment;
    TextView measurementsTextView;
    TextView aveMeasureTextView;
    TextView nameTextView;
    Button enterButton;
    Button saveButton;
    int numMeasurements;
    double aveMeasurement;
    String aveMeasurementString;
    double currentMeasurement;
    double totalMeasurement;
    EditText measurementInput;
    private Location location;
    private LocationHandler locationHandler;

    private static DecimalFormat df = new DecimalFormat("0.00");

    private List<MeasurementTrial> trials = new ArrayList<>();



    public MeasurementTrialFragment() {

    }

    public static MeasurementTrialFragment newInstance() {
        MeasurementTrialFragment fragment = new MeasurementTrialFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.measurement_trial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        experiment = (Experiment) bundle.getSerializable("Experiment");

        if(experiment.isLocationRequired()) {
            new LocationPopupFragment().newInstance(experiment).show(getFragmentManager(), "LocationPopup");
            locationHandler = new LocationHandler(getActivity().getApplicationContext());
            if(locationHandler.hasGPSPermissions()) {
                locationHandler.getCurrentLocation(new LocationCallback() {
                    @Override
                    public void callbackResult(Location loc) {
                        location = loc;
                    }
                });
            }
        }

        measurementExperiment = (MeasurementExperiment) experiment;
        user = (User) bundle.getSerializable("User");

        measurementsTextView = view.findViewById(R.id.num_measurements_value_textView);
        aveMeasureTextView = view.findViewById(R.id.ave_measurement_value_textView);
        enterButton = view.findViewById(R.id.measurement_button_enter);
        saveButton = view.findViewById(R.id.measurement_button_save);
        measurementInput = view.findViewById(R.id.measurement_value_editText);
        nameTextView = view.findViewById(R.id.measure_trial_TextView);
        nameTextView.setText(experiment.getName());

        if(measurementExperiment.isEnded()) {
            measurementInput.setEnabled(false);
            measurementInput.setText("Experiment Ended");
        }

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(measurementExperiment.isEnded()) {
                    Toast.makeText(view.getContext(), "Experiment Has Ended!", Toast.LENGTH_LONG).show();
                } else {
                    if(measurementInput.getText().toString().matches("")) {
                        Toast.makeText(view.getContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    numMeasurements++;
                    currentMeasurement = Double.parseDouble(measurementInput.getText().toString());
                    totalMeasurement += currentMeasurement;
                    calculateAverage();
                    trials.add(new MeasurementTrial(user.getUid(), new Date(), currentMeasurement, location, measurementExperiment.getExperimentID()));
                    measurementsTextView.setText(String.valueOf(numMeasurements));
                    aveMeasureTextView.setText(aveMeasurementString);
                    measurementInput.setText("");
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(measurementExperiment.isEnded()) {
                    Toast.makeText(view.getContext(), "Experiment Has Ended!", Toast.LENGTH_LONG).show();
                } else {
                    handler.updateExperiment(measurementExperiment);
                    for (Trial trial : trials) {
                        Log.v(String.valueOf(trial.getExperimentID()), "Trial experiment id");
                        handler.addTrial(trial, new addTrialCallBack() {
                            @Override
                            public void callBackResult(String trialID) {
                                Log.v(trialID, "Trial ID returned");
                            }
                        });
                    }

                    trials = new ArrayList<>();
                    numMeasurements = 0;
                    aveMeasurement = 0;
                    aveMeasurementString = "";
                    currentMeasurement = 0;
                    totalMeasurement = 0;

                    aveMeasureTextView.setText("0");
                    measurementsTextView.setText("0");
                }
            }
        });

    }


    private void calculateAverage() {
        aveMeasurement = (double)totalMeasurement/(double)numMeasurements;
        aveMeasurementString = df.format(aveMeasurement);
    }
}
