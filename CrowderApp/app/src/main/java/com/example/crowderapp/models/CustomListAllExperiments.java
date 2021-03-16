package com.example.crowderapp.models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
public class CustomListAllExperiments extends ArrayAdapter<Experiment> {

    private List<Experiment> experiments;
    private List<String> subscribedExps;
    private Context context;

    public CustomListAllExperiments(Context context, List<Experiment> experiments, List<String> subscribedExps) {
        super(context,0,experiments);
        this.experiments = experiments;
        this.context = context;
        this.subscribedExps = subscribedExps;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.all_experiments_custom_list, parent, false);
        }

        Experiment experiment = experiments.get(position);
        TextView expName = view.findViewById(R.id.allExpNameText);
        CheckBox subscribed = view.findViewById(R.id.subscribedButton);
        subscribed.setTag(position);
        subscribed.setOnCheckedChangeListener(checkListener);

        expName.setText(experiment.getName());
        if (subscribedExps.contains(experiment.getName())) {
            subscribed.setChecked(true);
        }
        else {
            subscribed.setChecked(false);
        }

        return view;
    }

    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = (int) buttonView.getTag();
            if(isChecked) {
                Log.d(String.valueOf(position), "onCheckedChanged: 1" );
                // TODO: Add expname to users subscribed list
            }
            else {
                // TODO: Remove expname from users subscribed list
                Log.d(String.valueOf(position), "onCheckedChanged: 0" );
            }
        }
    };
}
