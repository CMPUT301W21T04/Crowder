package com.example.crowderapp.views.trialfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.LocationHandler;
import com.example.crowderapp.controllers.callbackInterfaces.LocationCallback;
import com.example.crowderapp.controllers.callbackInterfaces.addTrialCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.CounterExperiment;
import com.example.crowderapp.models.CounterTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Location;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.LocationPopupFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CountTrialFragment extends TrialFragment {
    TextView totalCountTextView;
    int totalCount;
    Button countButton;
    Button saveButton;
    private CounterExperiment countExperiment;
    private Location location;
    private LocationHandler locationHandler;
    private List<CounterTrial> trials = new ArrayList<>();



    public CountTrialFragment() {

    }

    public static CountTrialFragment newInstance() {
        CountTrialFragment fragment = new CountTrialFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.count_trial_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        experiment = (Experiment) bundle.getSerializable("Experiment");

        locationHandler = new LocationHandler(getActivity().getApplicationContext());
        if(experiment.isLocationRequired()) {
            new LocationPopupFragment().newInstance(experiment).show(getFragmentManager(), "LocationPopup");
            if(locationHandler.hasGPSPermissions()) {
                locationHandler.getCurrentLocation(new LocationCallback() {
                    @Override
                    public void callbackResult(Location loc) {
                        location = loc;
                    }
                });
            }
        }

        countExperiment = (CounterExperiment) experiment;
        user = (User) bundle.getSerializable("User");
        totalCountTextView = view.findViewById(R.id.count_total_TextView);
        countButton = view.findViewById(R.id.count_button);
        saveButton = view.findViewById(R.id.count_save_Button);



        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countExperiment.isEnded()) {
                    Toast.makeText(view.getContext(), "Experiment Has Ended!", Toast.LENGTH_LONG).show();
                } else {
                    totalCount++;
                    totalCountTextView.setText(String.valueOf(totalCount));
                    countExperiment.incrementCount(user.getUid(), location);
                    trials.add(new CounterTrial(user.getUid(), new Date(), location, countExperiment.getExperimentID()));
                }

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countExperiment.isEnded()) {
                    Toast.makeText(view.getContext(), "Experiment Has Ended!", Toast.LENGTH_LONG).show();
                } else {
                    handler.updateExperiment(countExperiment);
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
                    totalCount = 0;

                    totalCountTextView.setText(String.valueOf(totalCount));
                }

            }
        });
    }
}
