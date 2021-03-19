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
import com.example.crowderapp.controllers.callbackInterfaces.endExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getUserByIDCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unsubscribedExperimentCallBack;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Location;
import com.example.crowderapp.models.MeasurementExperiment;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.MyExperimentsFragment;
import com.example.crowderapp.views.QuestionsFragment;

import java.util.ArrayList;
import java.util.List;

public class TrialFragment extends Fragment {

    User user;
    Experiment experiment;
    Menu menu;
    ExperimentHandler handler = new ExperimentHandler();
    UserHandler userHandler;

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
        userHandler = new UserHandler(getActivity().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));
        this.menu = menu;
        userHandler.getCurrentUser(new getUserByIDCallBack() {
            @Override
            public void callBackResult(User user) {
                MenuItem menuItem = menu.findItem(R.id.more_item);
                menuItem.setVisible(true);
                if(experiment.isEnded() || !experiment.getOwnerID().matches(user.getUid()) ) {
                    menu.findItem(R.id.end_item).setVisible(false);
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location_item:
//                Log.e("yo", "yo");
                break;
            case R.id.unpublish_item:
                handler.unPublishExperiment(experiment.getExperimentID(), new unPublishExperimentCallBack() {
                    @Override
                    public void callBackResult() {
                        userHandler.unsubscribeExperiment(experiment.getExperimentID(), new unsubscribedExperimentCallBack() {
                            @Override
                            public void callBackResult() {
                                openFragment(MyExperimentsFragment.newInstance());
                            }
                        });
                    }
                });
                break;
            case R.id.barcode_item:
                // TODO barcode
                break;
            case R.id.qr_code_item:
                //TODO qr code
                break;
            case R.id.comment_item:
                // TODO go to comments
                openFragmentWithExperimentID(QuestionsFragment.newInstance());
                break;
            case R.id.plot_item:
                // TODO show plots
                break;
            case R.id.histogram_item:
                // TODO show hist
                break;
            case R.id.stats_item:
                // TODO show stats
            case R.id.end_item:
                handler.endExperiment(experiment);
                item.setVisible(false);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void openFragmentWithExperimentID(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ExperimentID", experiment.getExperimentID());
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}