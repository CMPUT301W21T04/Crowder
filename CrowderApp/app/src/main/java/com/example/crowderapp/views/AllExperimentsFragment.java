package com.example.crowderapp.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.models.CustomListAllExperiments;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class AllExperimentsFragment extends Fragment {

    UserHandler userHandler;
    User user;
    private ListView allExpView;
    private ArrayAdapter<Experiment> allExpAdapter;
    private List<Experiment> allExpDataList = new ArrayList<Experiment>();
    private ExperimentHandler handler = ExperimentHandler.getInstance();
    private Context thisContext;
    List<String> subscribed = new ArrayList<String>();
    Task allExpTask;

    public AllExperimentsFragment() {

    }

    public static AllExperimentsFragment newInstance() {
        AllExperimentsFragment fragment = new AllExperimentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userHandler = new UserHandler(getActivity().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisContext = container.getContext();
        View view = inflater.inflate(R.layout.all_experiments_fragment, container, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        allExpTask = handler.getAllExperiments();
        allExpTask.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (allExpTask.isSuccessful()) {
                    allExpDataList = (List<Experiment>) task.getResult();
                    allExpAdapter = new CustomListAllExperiments(thisContext, allExpDataList, subscribed);
                    allExpView = getView().findViewById(R.id.all_experiment_list);

                    allExpView.setAdapter(allExpAdapter);
                }
                else {
                    Exception exception = task.getException();
                }
            }
        });
        userHandler.observeCurrentUser(getActivity(), (dao, user) -> {
            subscribed = user.getSubscribedExperiments();

        });
    }
}
