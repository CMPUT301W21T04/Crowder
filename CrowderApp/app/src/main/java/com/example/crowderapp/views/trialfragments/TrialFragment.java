package com.example.crowderapp.views.trialfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.models.Experiment;

public class TrialFragment extends Fragment {

    Experiment experiment;
    private ExperimentHandler handler = ExperimentHandler.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
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
}
