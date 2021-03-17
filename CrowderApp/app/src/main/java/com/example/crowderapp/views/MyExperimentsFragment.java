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
import com.example.crowderapp.controllers.callbackInterfaces.allExperimentsCallBack;
import com.example.crowderapp.models.CustomListAllExperiments;
import com.example.crowderapp.models.CustomListMyExperiments;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MyExperimentsFragment extends Fragment {

    UserHandler userHandler;
    User user;
    private ListView myExpView;
    private ArrayAdapter<Experiment> myExpAdapter;
    private List<Experiment> allExpDataList = new ArrayList<Experiment>();
    private ExperimentHandler handler = ExperimentHandler.getInstance();
    private Context thisContext;
    private List<String> subscribed = new ArrayList<String>();
    private List<Experiment> subExperiments = new ArrayList<Experiment>();

    Task userTask;

    public MyExperimentsFragment() {

    }

    public static MyExperimentsFragment newInstance() {
        MyExperimentsFragment fragment = new MyExperimentsFragment();
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
        View view = inflater.inflate(R.layout.my_experiments_fragment, container, false);
        return view;
    }

    private void createSubList() {
        for(Experiment exp : allExpDataList) {
            if (subscribed.contains(exp.getExperimentID())) {
                subExperiments.add(exp);
            }
        }
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

                    handler.getAllExperiments(new allExperimentsCallBack() {
                        @Override
                        public void callBackResult(List<Experiment> experimentList) {
                            allExpDataList = experimentList;
                            createSubList();
                            myExpAdapter = new CustomListMyExperiments(thisContext, subExperiments);
                            myExpView = getView().findViewById(R.id.my_experiment_list);
                            myExpView.setAdapter(myExpAdapter);
                        }
                    });
                }
                else {
                    Exception exception = task.getException();
                }

            }
        });
    }
}
