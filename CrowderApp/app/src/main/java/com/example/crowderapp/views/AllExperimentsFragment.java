package com.example.crowderapp.views;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowderapp.MainActivity;
import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.controllers.adapters.SearchListAdapter;
import com.example.crowderapp.controllers.callbackInterfaces.allExperimentsCallBack;
import com.example.crowderapp.models.AllExperimentListItem;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.CustomListAllExperiments;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Search;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.trialfragments.BinomialTrialFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.ChipGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllExperimentsFragment extends Fragment {

    public UserHandler userHandler;

    User user;
    private ListView allExpView;
    private ArrayAdapter<AllExperimentListItem> allExpAdapter;
    private ArrayAdapter<AllExperimentListItem> searchListAdapter;
    private List<Experiment> allExpDataList = new ArrayList<Experiment>();
    private List<AllExperimentListItem> allExperimentListItems = new ArrayList<AllExperimentListItem>();
    private ExperimentHandler handler = new ExperimentHandler();
    private Context thisContext;
    List<String> subscribed = new ArrayList<String>();

    // Search box
    private EditText searchEditText;
    private Button searchBtn;

    Task<User> userTask;
    MenuItem menuItem;


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
                            updateSubs();
                            allExpAdapter = new CustomListAllExperiments(thisContext, allExperimentListItems, checkListener);
                            allExpView = getView().findViewById(R.id.all_experiment_list);
                            allExpView.setAdapter(allExpAdapter);
                            allExpView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    openFragment(CommentFragment.newInstance());
                                }
                            });
                        }
                    });
                }
                else {
                    Exception exception = task.getException();
                }

            }
        });

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

        // Search Setup
        searchEditText = view.findViewById(R.id.search_EditText);
        searchBtn = view.findViewById(R.id.search_btn);
        // Init search adapter
        searchListAdapter = new SearchListAdapter(thisContext, allExperimentListItems, checkListener);

        searchBtn.setOnClickListener(v -> handleSearch());

        return view;
    }

    /**
     * To be called when the search button is clicked.
     * Retrieves all experiments again and filters by search string.
     */
    private void handleSearch() {
        User theUser = userTask.getResult();
        if (theUser == null) {
            // Current user hasn't bee retrieved so ignore search command for now.
            return;
        }
        if (allExpAdapter == null) {
            // The first experiment list hasn't even be made yet! Ignore command.
            return;
        }

        // Delimit search string by space.
        String[] searchArray = searchEditText.getText().toString().split(" ");
        ArrayList<String> searchList = new ArrayList<>(Arrays.asList(searchArray));

        handler.getAllExperiments(experimentList -> {
            Search searcher = new Search();
            allExpDataList = searcher.searchExperiments(searchList, experimentList);

            allExperimentListItems.clear();
            updateSubs();

            if (searchList.isEmpty() || searchList.get(0).isEmpty()) { // Also check if first index is just empty string
                // Nothing to search! Go back to normal list.
                allExpView.setAdapter(allExpAdapter);
                allExpAdapter.notifyDataSetChanged();
            }
            else {
                // Search mode on
                allExpView.setAdapter(searchListAdapter);
                searchListAdapter.notifyDataSetChanged();
            }

        });
    }

    private boolean alreadyExists(Experiment exp) {
        for(AllExperimentListItem expItem : allExperimentListItems) {
            if(expItem.getExperiment().getExperimentID().equals(exp.getExperimentID())) {
                return true;
            }
        }
        return false;
    }

    private void updateSubs() {
        for(Experiment exp : allExpDataList) {
            if (!alreadyExists(exp)) {
                if (subscribed.contains(exp.getExperimentID())) {
                    allExperimentListItems.add(new AllExperimentListItem(exp, true));
                } else {
                    allExperimentListItems.add(new AllExperimentListItem(exp, false));
                }
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        userTask = userHandler.getCurrentUser();
//        userTask.addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                if (userTask.isSuccessful()) {
//                    user = (User) task.getResult();
//                    subscribed = user.getSubscribedExperiments();
//
//                    handler.getAllExperiments(new allExperimentsCallBack() {
//                        @Override
//                        public void callBackResult(List<Experiment> experimentList) {
//                            allExpDataList = experimentList;
//                            updateSubs();
//                            allExpAdapter = new CustomListAllExperiments(thisContext, allExperimentListItems, checkListener);
//                            allExpView = getView().findViewById(R.id.all_experiment_list);
//                            allExpView.setAdapter(allExpAdapter);
//                            allExpView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                                    ((MainActivity)getActivity()).fab.hide();
//                                }
//                            });
//                        }
//                    });
//                }
//                else {
//                    Exception exception = task.getException();
//                }
//
//            }
//        });
    }
}
