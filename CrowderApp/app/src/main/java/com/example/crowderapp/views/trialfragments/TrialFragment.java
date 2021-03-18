package com.example.crowderapp.views.trialfragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.views.MyExperimentsFragment;

public class TrialFragment extends Fragment {

    Experiment experiment;
    private ExperimentHandler handler = new ExperimentHandler();
    private UserHandler userHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        userHandler = new UserHandler(getActivity().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));

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
                Log.e(":", "We are going to unpublish");
                handler.unPublishExperiment(experiment.getExperimentID(), new unPublishExperimentCallBack() {
                    @Override
                    public void callBackResult() {
                        userHandler.unsubscribeExperiment(experiment.getExperimentID());
                        openFragment(MyExperimentsFragment.newInstance());
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
