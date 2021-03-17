package com.example.crowderapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowderapp.R;

import java.util.List;

public class CustomListMyExperiments extends ArrayAdapter<Experiment> {
    private List<Experiment> experiments;
    private Context context;

    public CustomListMyExperiments(Context context, List<Experiment> experiments) {
        super(context, 0, experiments);
        this.context = context;
        this.experiments = experiments;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.my_experiments_custom_list, parent, false);
        }

        Experiment experiment = experiments.get(position);
        TextView expName = view.findViewById(R.id.myExpNameText);
        expName.setText(experiment.getName());


        return view;
    }
}