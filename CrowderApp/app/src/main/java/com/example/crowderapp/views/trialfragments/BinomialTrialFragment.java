package com.example.crowderapp.views.trialfragments;

import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
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
import com.example.crowderapp.controllers.callbackInterfaces.addTrialCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.models.BinomialExperiment;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Location;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BinomialTrialFragment extends Fragment {
    private User user;
    private BinomialExperiment experiment;
    private ExperimentHandler handler = ExperimentHandler.getInstance();
    private List<BinomialTrial> trials = new ArrayList<BinomialTrial>();
    private Location location = new Location();
    private int succView;
    private int failView;
    private double succRateView;

    public BinomialTrialFragment() {

    }

    public static BinomialTrialFragment newInstance() {
        BinomialTrialFragment fragment = new BinomialTrialFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        location.setLatitude(53.49591130578844);
        location.setLongitude(-113.52409008650696);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // You can hide the state of the menu item here if you call getActivity().supportInvalidateOptionsMenu(); somewhere in your code
        MenuItem menuItem = menu.findItem(R.id.more_item);
        menuItem.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location_item:
                Log.e("yo", "yo");
                break;
            case R.id.unpublish_item:
                handler.unPublishExperiment(experiment.getExperimentID(), new unPublishExperimentCallBack() {
                    @Override
                    public void callBackResult() {
                        getFragmentManager().popBackStack();
                    }
                });


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.binomial_trial_fragment, container, false);
    }

    // Update the success rate
    private void updateSuccessRate() {
        // Removes possibility of divide by 0
        if (this.succView+this.failView == 0) {
            this.succRateView = 0;
        }
        else {
            this.succRateView = new Double(Double.valueOf(this.succView)/(Double.valueOf(this.succView) + Double.valueOf(this.failView))) * 100;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        experiment = (BinomialExperiment) bundle.getSerializable("Experiment");
        user = (User) bundle.getSerializable("User");

        this.succView = 0;
        this.failView = 0;
        this.succRateView = 0.0;

        // Get UI elements
        TextView expName = view.findViewById(R.id.binomialExpName);
        TextView passes = view.findViewById(R.id.binomialPasses);
        TextView fails = view.findViewById(R.id.binomialFails);
        TextView successRate = view.findViewById(R.id.binomialSuccessRate);

        // Set initial values for the experiment trials
        expName.setText(experiment.getName());
        passes.setText("Successes: " + String.valueOf(succView));
        fails.setText("Fails : " + String.valueOf(failView));
        successRate.setText("Success Rate: " + String.valueOf(succRateView));

        Button save = view.findViewById(R.id.binomialSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Toast message saying trials were added??
                handler.updateExperiment(experiment);
                for(Trial trial : trials) {
                    Log.v(String.valueOf(trial.getExperimentID()), "Trial experiment id");
                    handler.addTrial(trial, new addTrialCallBack() {
                        @Override
                        public void callBackResult(String trialID) {
                            Log.v(trialID, "Trial ID returned");
                        }
                    });
                }
                // Reset values
                trials = new ArrayList<>();
                succView = 0;
                failView = 0;
                succRateView = 0;
                updateSuccessRate();

                // Refresh UI elements to 0
                passes.setText("Successes: " + String.valueOf(succView));
                fails.setText("Fails : " + String.valueOf(failView));
                successRate.setText("Success Rate: " + String.valueOf(succRateView));
                passes.invalidate();
                fails.invalidate();
                successRate.invalidate();
            }
        });

        Button successBtn = view.findViewById(R.id.binomialPassButton);
        successBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Update Passes and Update Success Rate
                //TODO: Add a success trial to the "trials" list
                experiment.addPass(user.getUid(), location);
                trials.add(new BinomialTrial(user.getUid(), new Date(), true, new Location(), experiment.getExperimentID()));
                succView += 1;
                updateSuccessRate();
                passes.setText("Successes: " + String.valueOf(succView));
                successRate.setText("Success Rate: " + String.valueOf(succRateView));
                passes.invalidate();
                successRate.invalidate();
            }
        });
        Button failBtn = view.findViewById(R.id.binomialFailButton);
        failBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Update Fails and Update Success Rate
                //TODO: Add a fail trial to the "trials" list
                experiment.addFail(user.getUid(), location);
                trials.add(new BinomialTrial(user.getUid(), new Date(), false, new Location(), experiment.getExperimentID()));
                failView += 1;
                updateSuccessRate();
                fails.setText("Fails : " + String.valueOf(failView));
                successRate.setText("Success Rate: " + String.valueOf(succRateView));
                fails.invalidate();
                successRate.invalidate();
            }
        });
    }
}
