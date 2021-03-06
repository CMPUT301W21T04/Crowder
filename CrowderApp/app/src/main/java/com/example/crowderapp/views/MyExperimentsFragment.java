package com.example.crowderapp.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.controllers.callbackInterfaces.allExperimentsCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getUserByIDCallBack;
import com.example.crowderapp.models.BinomialExperiment;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.CustomListAllExperiments;
import com.example.crowderapp.models.CustomListMyExperiments;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.trialfragments.BinomialTrialFragment;
import com.example.crowderapp.views.trialfragments.CountTrialFragment;
import com.example.crowderapp.views.trialfragments.MeasurementTrialFragment;
import com.example.crowderapp.views.trialfragments.NonNegativeCountTrialFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays experiments a user is subscribed to
 */
public class MyExperimentsFragment extends Fragment {

    UserHandler userHandler;
    User thisUser;
    private ListView myExpView;
    private ArrayAdapter<Experiment> myExpAdapter;
    private List<Experiment> allExpDataList = new ArrayList<Experiment>();
    private ExperimentHandler handler = new ExperimentHandler();
    private Context thisContext;
    private List<String> subscribed = new ArrayList<String>();
    private List<Experiment> subExperiments = new ArrayList<Experiment>();
    Experiment currentExperiment;

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

        userHandler.getCurrentUser(new getUserByIDCallBack() {
            @Override
            public void callBackResult(User user) {
                thisUser = user;
                subscribed = user.getSubscribedExperiments();

                handler.getAllExperiments(new allExperimentsCallBack() {
                    @Override
                    public void callBackResult(List<Experiment> experimentList) {
                        allExpDataList = experimentList;
                        createSubList();
                        myExpAdapter = new CustomListMyExperiments(thisContext, subExperiments);
                        myExpView = getView().findViewById(R.id.my_experiment_list);
                        myExpView.setAdapter(myExpAdapter);

                        // Open a fragment based on experiment type
                        myExpView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                currentExperiment = subExperiments.get(position);
                                String experimentType = currentExperiment.getExperimentType();
                                switch (experimentType) {
                                    case "Count":
                                        openFragment(CountTrialFragment.newInstance());
                                        break;
                                    case "Binomial":
                                        openFragment(BinomialTrialFragment.newInstance());
                                        break;
                                    case "Non-Negative Integer":
                                        openFragment(NonNegativeCountTrialFragment.newInstance());
                                        break;
                                    case "Measurement":
                                        openFragment(MeasurementTrialFragment.newInstance());
                                        break;
                                    default:
                                        break;

                                }
                            }
                        });
                    }
                });
            }
        });
        return view;
    }


    /**
     * Create a list of experiments a user is subscribed to
     */
    private void createSubList() {
        for(Experiment exp : allExpDataList) {
            if (subscribed.contains(exp.getExperimentID()) && !subExperiments.contains(exp.getExperimentID())) {
                subExperiments.add(exp);
            }
        }
    }

    /**
     * Opens a fragment with arguments
     * @param fragment fragment to open
     */
    public void openFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Experiment", currentExperiment);
        bundle.putSerializable("User", thisUser);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        subExperiments.clear();
    }
}
