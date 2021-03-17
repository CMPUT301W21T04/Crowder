package com.example.crowderapp.views.trialfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.models.Experiment;

public class CountTrialFragment extends Fragment {

    Experiment experiment;
    private ExperimentHandler handler = ExperimentHandler.getInstance();

    public CountTrialFragment() {

    }

    public static CountTrialFragment newInstance() {
        CountTrialFragment fragment = new CountTrialFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

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
        return inflater.inflate(R.layout.count_trial_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        experiment = (Experiment) bundle.getSerializable("Experiment");
    }
}
