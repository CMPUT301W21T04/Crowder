package com.example.crowderapp.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    UserHandler userHandler;

    User user;
    private ListView allExpView;
    private ArrayAdapter<AllExperimentListItem> allExpAdapter;
    private List<Experiment> allExpDataList = new ArrayList<Experiment>();
    private List<AllExperimentListItem> allExperimentListItems = new ArrayList<AllExperimentListItem>();
    private ExperimentHandler handler = ExperimentHandler.getInstance();
    private Context thisContext;
    List<String> subscribed = new ArrayList<String>();

    Task userTask;
    Task allExpTask;

    public AllExperimentsFragment() {

    }

    public static AllExperimentsFragment newInstance() {
        AllExperimentsFragment fragment = new AllExperimentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = (int) buttonView.getTag();
            Experiment exp = allExpDataList.get(position);
            if(isChecked) {
                userHandler.subscribeExperiment(exp.getExperimentID());
                Log.v(String.valueOf(exp.getExperimentID()), "Subscribed to: ");
            }
            else {
                userHandler.unsubscribeExperiment(exp.getExperimentID());
                Log.v(String.valueOf(exp.getExperimentID()), "Unsubscribed to: ");
            }
            Log.v(String.valueOf(user.getUid()), "Current User");
            Log.v(String.valueOf(isChecked), "Button changed");
            Log.v(String.valueOf(position), "At this position");
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        userHandler = new UserHandler(getActivity().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisContext = container.getContext();
        View view = inflater.inflate(R.layout.all_experiments_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        userTask = userHandler.getCurrentUser();
        userTask.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (userTask.isSuccessful()) {
                    user = (User) task.getResult();
                    subscribed = user.getSubscribedExperiments();

                    allExpTask = handler.getAllExperiments();
                    allExpTask.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (allExpTask.isSuccessful()) {
                                allExpDataList = (List<Experiment>) task.getResult();
                                for(Experiment exp : allExpDataList) {
                                    if(subscribed.contains(exp.getExperimentID())) {
                                        allExperimentListItems.add(new AllExperimentListItem(exp, true));
                                    }
                                    else {
                                        allExperimentListItems.add(new AllExperimentListItem(exp, false));
                                    }
                                }
                                allExpAdapter = new CustomListAllExperiments(thisContext, allExperimentListItems, checkListener);
                                allExpView = getView().findViewById(R.id.all_experiment_list);
                                allExpView.setAdapter(allExpAdapter);
                                allExpView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        openFragment(BinomialTrialFragment.newInstance());
                                    }
                                });
                            }
                            else {
                                Exception exception = task.getException();
                            }
                        }
                    });
                }
                else {
                    Exception exception = task.getException();
                }

            }
        });


//        sub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int position = (int) buttonView.getTag();
//                if(isChecked) {
//                    Log.e(String.valueOf(position), "onCheckedChanged: 1" );
//                    // TODO: Add expname to users subscribed list
//                }
//                else {
//                    // TODO: Remove expname from users subscribed list
//                    Log.e(String.valueOf(position), "onCheckedChanged: 0" );
//                }
//            }
//        });

        userHandler.observeCurrentUser(getActivity(), (dao, user) -> {
            subscribed = user.getSubscribedExperiments();

        });
    }
}
