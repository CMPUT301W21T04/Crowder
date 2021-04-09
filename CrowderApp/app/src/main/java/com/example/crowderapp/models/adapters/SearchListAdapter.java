package com.example.crowderapp.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private final View.OnClickListener subListener;
    private final UsernameClickCallback userListener;
    private UserHandler handler;

    /**
     * Callback for clicking a user.
     */
    public interface UsernameClickCallback {
        void userClicked(String userId);
    }


    /**
     * Constructor.
     * @param context Android Context.
     * @param experiments List of Experiment List Items.
     * @param subListener The listener for when subscribe is clicked.
     * @param userListener The callback for when username is clicked.
     */
    public SearchListAdapter(Context context, List<AllExperimentListItem> experiments, View.OnClickListener subListener, UsernameClickCallback userListener) {
        super(context,0,experiments);
        this.experiments = experiments;
        this.context = context;
        this.subListener = subListener;
        this.userListener = userListener;
        handler = new UserHandler(getContext().getSharedPreferences(UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));
    }

    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.all_experiments_search_result, parent, false);
            view.setTag(R.string.update_index_tag, 0);
        }

        AllExperimentListItem experimentItem = experiments.get(position);
        Experiment experiment = experimentItem.getExperiment();

        TextView expName = view.findViewById(R.id.search_experiment_name);
        Button subscribed = view.findViewById(R.id.search_subscribed);
        TextView status = view.findViewById(R.id.search_status);
        TextView username = view.findViewById(R.id.search_user);
        TextView type = view.findViewById(R.id.search_type);

        // Init subscribe box
        subscribed.setTag(position);
        subscribed.setOnClickListener(subListener);

        expName.setText(experiment.getName());
        status.setText(experiment.isEnded() ? "Ended" : "Active");
        type.setText(experiment.getExperimentType());

        // Fetch username
        if (experiment.getOwnerID() != null && !experiment.getOwnerID().isEmpty()) {

            /*
                Multiple asyncs can cause race conditions. Fix by having each associated with
                a update index. If the update index is higher than expected, then a more
                recent async has been launched.
             */
            final int targetTag = (Integer) view.getTag(R.string.update_index_tag) + 1;
            view.setTag(R.string.update_index_tag, targetTag);
            final View targetView = view;

            handler.getUserByID(experiment.getOwnerID(), user -> {
                if ((Integer) targetView.getTag(R.string.update_index_tag) != targetTag) {
                    // A more recent async has been launched. Don't do anything!.
                    return;
                }

                if (user == null) {
                    // SOMEONE MESSED UP THE DB!
                    username.setText("null");
                }
                else {
                    username.setText(user.getName());
                    username.setOnClickListener((v) -> {
                        userListener.userClicked(user.getUid());
                    });
                }
            });
        }
        else {
            username.setText("null");
        }

        if (experimentItem.getIsSubscribed()) {
            subscribed.setBackgroundColor(context.getResources().getColor(R.color.black));
        }
        else {
            subscribed.setBackgroundColor(context.getResources().getColor(R.color.teal_200));
        }

        return view;
    }
}
