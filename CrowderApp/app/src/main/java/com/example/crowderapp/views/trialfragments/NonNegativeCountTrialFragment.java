package com.example.crowderapp.views.trialfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.addTrialCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.models.CounterTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Location;
import com.example.crowderapp.models.TallyExperiment;
import com.example.crowderapp.models.TallyTrial;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.AllExperimentsFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class NonNegativeCountTrialFragment extends TrialFragment {
    TallyExperiment tallyExperiment;

    TextView numCountTextView;
    TextView aveCountTextView;
    EditText integerValueEditText;
    String integerValueString;
    Button enterButton;
    Button saveButton;

    private static DecimalFormat df = new DecimalFormat("0.00");

    int numCounts;
    int currentCount;
    double average;
    String averageString;

    private List<TallyTrial> trials = new ArrayList<>();



    public NonNegativeCountTrialFragment() {

    }

    public static NonNegativeCountTrialFragment newInstance() {
        NonNegativeCountTrialFragment fragment = new NonNegativeCountTrialFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nonnegative_integer_count_trial_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        experiment = (Experiment) bundle.getSerializable("Experiment");
        tallyExperiment = (TallyExperiment) experiment;
        user = (User) bundle.getSerializable("User");

        numCountTextView = view.findViewById(R.id.num_non_neg_value_textView);
        aveCountTextView = view.findViewById(R.id.ave_non_neg_value_textView);
        integerValueEditText = view.findViewById(R.id.non_neg_value_editText);
        enterButton = view.findViewById(R.id.non_neg_button_enter);
        saveButton = view.findViewById(R.id.non_neg_button_save);

        if(tallyExperiment.isEnded()) {
            integerValueEditText.setEnabled(false);
            integerValueEditText.setText("Experiment Ended");
        }

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tallyExperiment.isEnded()) {
                    Toast.makeText(view.getContext(), "Experiment Has Ended!", Toast.LENGTH_LONG).show();
                } else {
                    numCountTextView.setText(String.valueOf(numCounts));
                    integerValueString = integerValueEditText.getText().toString();
                    currentCount = Integer.valueOf(integerValueString);
                    calculateAverage();
                    numCounts++;
                    tallyExperiment.addNonNegativeCount(currentCount, user.getUid(), new Location());
                    aveCountTextView.setText(averageString);
                    integerValueEditText.setText("");
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tallyExperiment.isEnded()) {
                    Toast.makeText(view.getContext(), "Experiment Has Ended!", Toast.LENGTH_LONG).show();
                } else {
                    handler.updateExperiment(tallyExperiment);
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
                    integerValueString = "";
                    numCounts = 0;
                    currentCount = 0;
                    average = 0;
                    averageString = "";

                    numCountTextView.setText("0");
                    aveCountTextView.setText("0");
                }
            }
        });


    }

    private void calculateAverage() {
        average = (average*numCounts+(double)currentCount)/(double)(1+numCounts);
        averageString = df.format(average);
    }


}
