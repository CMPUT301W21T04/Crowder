package com.example.crowderapp.views.trialfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowderapp.HeatmapActivity;
import com.example.crowderapp.R;
import com.example.crowderapp.ScanActivity;
import com.example.crowderapp.StatsActivity;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.controllers.callbackInterfaces.GetUserListCallback;
import com.example.crowderapp.controllers.callbackInterfaces.endExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getUserByIDCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unsubscribedExperimentCallBack;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Location;
import com.example.crowderapp.models.MeasurementExperiment;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.BinomialBarcodeFragment;
import com.example.crowderapp.views.LocationPopupFragment;
import com.example.crowderapp.views.MeasurementBarcodeFragment;
import com.example.crowderapp.views.MyExperimentsFragment;
import com.example.crowderapp.views.NonNegBarcodeFragment;
import com.example.crowderapp.views.QuestionsFragment;
import com.example.crowderapp.views.UserFilterFragment;

import java.util.ArrayList;
import java.util.List;

public class TrialFragment extends Fragment {


    User user;
    Experiment experiment;
    String experimentType;
    Menu menu;
    ExperimentHandler handler = new ExperimentHandler();
    UserHandler userHandler;
    int curIndex=0;


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
                boolean isOwner = experiment.getOwnerID().matches(user.getUid());
                if(experiment.isEnded() || !isOwner ) {
                    menu.findItem(R.id.end_item).setVisible(false);
                }
                if(!isOwner) {
                    menu.findItem(R.id.unpublish_item).setVisible(false);
                    menu.findItem(R.id.filter_item).setVisible(false);
                }
                if(!experiment.isLocationRequired()) {
                    menu.findItem(R.id.location_item).setVisible(false);
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location_item:
                Intent intentLocation = new Intent(getActivity(), HeatmapActivity.class);
                intentLocation.putExtra("experiment", experiment);
                startActivity(intentLocation);
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
            case R.id.assign_barcode_item:
                if(experimentType.equals("Binomial"))
                    new BinomialBarcodeFragment().show(getFragmentManager(), "BinomialBarcode");
                else if(experimentType.equals("NonNeg"))
                    new NonNegBarcodeFragment().show(getFragmentManager(), "NonNegBarcode");
                else if(experimentType.equals("Count"))
                    launchScanner();
                else if(experimentType.equals("Measurement"))
                    new MeasurementBarcodeFragment().show(getFragmentManager(), "NonNegBarcode");
                // TODO barcode
                break;
            case R.id.scan_item:
                launchScanner();
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
                Intent statsIntent =  new Intent(getActivity(), StatsActivity.class);
                startActivity(statsIntent);
                break;
            case R.id.end_item:
                handler.endExperiment(experiment);
                item.setVisible(false);
                break;

            case R.id.filter_item:
                createFilterFragment();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void createFilterFragment() {

        handler.refreshExperimentTrials(experiment, new getExperimentCallBack() {
            @Override
            public void callBackResult(Experiment exp) {
                experiment = exp;
                String userKey;
                List<Trial> trials = experiment.getTrials();
                List<String> usersIDs = new ArrayList<String>();
                for(int i = 0; i < trials.size(); i++) {
                    curIndex = i;
                    userKey = trials.get(i).getExperimenter();
                    if (!usersIDs.contains(userKey)) { // If the user does not already exist in the list, add it
                        usersIDs.add(userKey);
                    }
                }

                userHandler.getUserListById(usersIDs, new GetUserListCallback() {
                    @Override
                    public void callBackResult(List<User> userList) {
                        new UserFilterFragment().newInstance(experiment, userList).show(getFragmentManager(), "UserFilter");

                    }
                });
            }
        });
    }

    private void launchScanner() {
        Intent intent = new Intent(getActivity(), ScanActivity.class);
        startActivity(intent);
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
//        transaction.addToBackStack(null);
        transaction.commit();
        getFragmentManager().popBackStack();
    }

}