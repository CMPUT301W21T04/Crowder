package com.example.crowderapp.controllers.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.models.AllExperimentListItem;
import com.example.crowderapp.models.Experiment;

import java.util.List;

public class SearchListAdapter extends ArrayAdapter<AllExperimentListItem> {
    private List<AllExperimentListItem> experiments;
    private Context context;
    private final CompoundButton.OnCheckedChangeListener listener;
    private UserHandler handler;


    public SearchListAdapter(Context context, List<AllExperimentListItem> experiments, CompoundButton.OnCheckedChangeListener listener) {
        super(context,0,experiments);
        this.experiments = experiments;
        this.context = context;
        this.listener = listener;
        handler = new UserHandler(getContext().getSharedPreferences(UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));
    }

    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.all_experiments_search_result, parent, false);
        }

        AllExperimentListItem experimentItem = experiments.get(position);
        Experiment experiment = experimentItem.getExperiment();

        TextView expName = view.findViewById(R.id.search_experiment_name);
        CheckBox subscribed = view.findViewById(R.id.search_subscribed);
        TextView status = view.findViewById(R.id.search_status);
        TextView username = view.findViewById(R.id.search_user);

        // Init subscribe box
        subscribed.setTag(position);
        subscribed.setOnCheckedChangeListener(listener);

        expName.setText(experiment.getName());
        status.setText(experiment.isEnded() ? "Ended" : "Active");

        // Fetch username
        if (experiment.getOwnerID() != null && !experiment.getOwnerID().isEmpty()) {
            handler.getUserByID(experiment.getOwnerID(), user -> {
                username.setText(user.getName());
            });
        }
        else {
            username.setText("null");
        }

        if (experimentItem.getIsSubscribed()) {
            subscribed.setChecked(true);
        } else {
            subscribed.setChecked(false);
        }

        return view;
    }
}
