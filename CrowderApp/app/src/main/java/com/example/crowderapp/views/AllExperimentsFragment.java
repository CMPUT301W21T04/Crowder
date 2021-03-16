package com.example.crowderapp.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.models.CustomListAllExperiments;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class AllExperimentsFragment extends Fragment {

    UserHandler userHandler;
    User user;
    private ListView allExpView;
    private ArrayAdapter<Experiment> allExpAdapter;
    private ExperimentHandler handler = ExperimentHandler.getInstance();
    private Context thisContext;

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
        userHandler.observeCurrentUser(getActivity(), (dao, user) -> {
            List<String> subscribed = user.getSubscribedExperiments();
            allExpAdapter = new CustomListAllExperiments(thisContext, handler.getAllExperiments(), subscribed);
        });

        allExpView = getView().findViewById(R.id.all_experiment_list);

        allExpView.setAdapter(allExpAdapter);
    }
}
