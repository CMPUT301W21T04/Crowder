package com.example.crowderapp.models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowderapp.R;

import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.models.Experiment;

/*
    Template taken from CMPUT 301 Lab 3 Demo code

    Custom list adapter to show multiple fields in the main list view
 */
public class CustomListAllExperiments extends ArrayAdapter<AllExperimentListItem> {

    private List<AllExperimentListItem> experiments;
    private Context context;
    private final View.OnClickListener listener;

    /**
     * Constructor
     * @param context context
     * @param experiments List of All Experiments to show
     * @param listener Listener for the subscribe button
     */
    public CustomListAllExperiments(Context context, List<AllExperimentListItem> experiments, View.OnClickListener listener) {
        super(context,0,experiments);
        this.experiments = experiments;
        this.context = context;
        this.listener = listener;
    }



    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.all_experiments_custom_list, parent, false);
        }

        AllExperimentListItem experimentItem = experiments.get(position);
        Experiment experiment = experimentItem.getExperiment();

        TextView expName = view.findViewById(R.id.allExpNameText);
        Button subscribed = view.findViewById(R.id.subscribeButton);
        TextView minTrials = view.findViewById(R.id.minTrials_text);
        TextView region = view.findViewById(R.id.region_text);

        subscribed.setTag(position);
        subscribed.setOnClickListener(listener);


        expName.setText(experiment.getName());
        minTrials.setText("Min. Trials: " + String.valueOf(experiment.getMinTrials()));
        region.setText("Region: " + experiment.getRegion());
        if (experimentItem.getIsSubscribed()) {
            subscribed.setBackgroundColor(context.getResources().getColor(R.color.black));
        }
        else {
            subscribed.setBackgroundColor(context.getResources().getColor(R.color.teal_200));
        }

        return view;
    }
}
