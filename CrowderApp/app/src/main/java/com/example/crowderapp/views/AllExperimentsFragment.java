package com.example.crowderapp.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowderapp.MainActivity;
import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.controllers.callbackInterfaces.allExperimentsCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getAllSubscribedExperimentsCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getUserByIDCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.subscribeExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unsubscribedExperimentCallBack;
import com.example.crowderapp.models.AllExperimentListItem;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.CustomListAllExperiments;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.trialfragments.BinomialTrialFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class AllExperimentsFragment extends Fragment {

    public UserHandler userHandler;

    User thisUser;
    private ListView allExpView;
    private ArrayAdapter<AllExperimentListItem> allExpAdapter;
    private List<Experiment> allExpDataList = new ArrayList<Experiment>();
    private List<AllExperimentListItem> allExperimentListItems = new ArrayList<AllExperimentListItem>();
    private ExperimentHandler handler = new ExperimentHandler();
    private Context thisContext;
    List<String> subscribed = new ArrayList<String>();

    Task userTask;
    MenuItem menuItem;

    public AllExperimentsFragment() {

    }

    public static AllExperimentsFragment newInstance() {
        AllExperimentsFragment fragment = new AllExperimentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            Experiment exp = allExpDataList.get(position);
            if(!subscribed.contains(exp.getExperimentID())) { // If not subscribed already
                userHandler.subscribeExperiment(exp.getExperimentID(), new subscribeExperimentCallBack() {
                    @Override
                    public void callBackResult() {
//                        getNewSubs();
                        v.setBackgroundColor(getResources().getColor(R.color.black));
                    }
                });
                Log.v(String.valueOf(exp.getExperimentID()), "Subscribed to: ");
            }
            else {
                userHandler.unsubscribeExperiment(exp.getExperimentID(), new unsubscribedExperimentCallBack() {
                    @Override
                    public void callBackResult() {
                        //getNewSubs();
                        v.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    }
                });
//                Log.v(String.valueOf(exp.getExperimentID()), "Unsubscribed to: ");
            }
//            getNewSubs();
//            Log.v(String.valueOf(thisUser.getUid()), "Current User");
//            Log.v(String.valueOf(v.getSolidColor()), "Button changed");
//            Log.v(String.valueOf(position), "At this position");
        }
    };


    public void getNewSubs() {
        if(thisUser != null) {
            subscribed = thisUser.getSubscribedExperiments();
            updateSubs();
            //allExpAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        userHandler = new UserHandler(getActivity().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));

        update();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisContext = container.getContext();
        View view = inflater.inflate(R.layout.all_experiments_fragment, container, false);
        return view;
    }

    private void updateSubs() {
        allExperimentListItems.clear();
        for(Experiment exp : allExpDataList) {
                if (subscribed.contains(exp.getExperimentID())) {
                    allExperimentListItems.add(new AllExperimentListItem(exp, true));
                } else {
                    allExperimentListItems.add(new AllExperimentListItem(exp, false));
                }
        }
    }

    public void update() {

        userHandler.getCurrentUser(new getUserByIDCallBack() {
            @Override
            public void callBackResult(User user) {
                thisUser = user;
                subscribed.clear();
                subscribed = user.getSubscribedExperiments();

                handler.getAllExperiments(new allExperimentsCallBack() {
                    @Override
                    public void callBackResult(List<Experiment> experimentList) {
                        allExpDataList = experimentList;
                        updateSubs();
                        allExpAdapter = new CustomListAllExperiments(thisContext, allExperimentListItems, listener);
                        allExpView = getView().findViewById(R.id.all_experiment_list);
                        allExpView.setAdapter(allExpAdapter);
                    }
                });
            }
        });
    }
}
