package com.example.crowderapp.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowderapp.MainActivity;
import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.models.adapters.SearchListAdapter;
import com.example.crowderapp.controllers.callbackInterfaces.allExperimentsCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getUserByIDCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.subscribeExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unsubscribedExperimentCallBack;
import com.example.crowderapp.models.AllExperimentListItem;
import com.example.crowderapp.models.CustomListAllExperiments;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Search;
import com.example.crowderapp.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main Fragment
 * Displays all experiments available to be subscribes to
 */
public class AllExperimentsFragment extends Fragment {

    public UserHandler userHandler;

    User thisUser;
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

    private FloatingActionButton fab;

    MenuItem menuItem;

    public AllExperimentsFragment() {

    }

    public static AllExperimentsFragment newInstance() {
        AllExperimentsFragment fragment = new AllExperimentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

/* Used as an idea of what do when clicking a subscribe button
URL: https://stackoverflow.com/questions/14782901/android-how-to-handle-button-click
Author: https://stackoverflow.com/users/2048952/dumamilk
Editor: https://stackoverflow.com/users/-1/community
License: CC BY-SA 3.0
 */
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Handle subscribing and unsubscribing to experiments
            int position = (int) v.getTag();
            Experiment exp = allExpDataList.get(position);
            if(!subscribed.contains(exp.getExperimentID())) { // If not subscribed already
                userHandler.subscribeExperiment(exp.getExperimentID(), new subscribeExperimentCallBack() {
                    @Override
                    public void callBackResult() {
                        allExperimentListItems.set(position, new AllExperimentListItem(exp, true));
                        v.setBackgroundColor(getResources().getColor(R.color.black));
                    }
                });
                Log.v(String.valueOf(exp.getExperimentID()), "Subscribed to: ");
            }
            else {
                userHandler.unsubscribeExperiment(exp.getExperimentID(), new unsubscribedExperimentCallBack() {
                    @Override
                    public void callBackResult() {
                        allExperimentListItems.set(position, new AllExperimentListItem(exp, false));
                        v.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    }
                });

            }
            allExpAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        userHandler = new UserHandler(getActivity().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thisContext = container.getContext();
        View view = inflater.inflate(R.layout.all_experiments_fragment, container, false);

        userHandler = new UserHandler(getActivity().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));

        update();

        // set floating action button
        fab = view.findViewById(R.id.add_experiment_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddExperimentFragment().show(getFragmentManager(), "ADD_EXPR");
            }
        });

        // Search Setup
        searchEditText = view.findViewById(R.id.search_EditText);
        searchBtn = view.findViewById(R.id.search_btn);
        // Init search adapter
        searchListAdapter = new SearchListAdapter(thisContext, allExperimentListItems, listener, userId -> openUserFragment(userId));

        searchBtn.setOnClickListener(v -> handleSearch());

        return view;
    }

    /**
     * Opens an immutable user fragment.
     * @param userId The ID of user whose profile to open
     */
    public void openUserFragment(String userId) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, ImmutableProfileFragment.newInstance(userId));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * To be called when the search button is clicked.
     * Retrieves all experiments again and filters by search string.
     */
    private void handleSearch() {
        if (thisUser == null) {
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

        handler.searchExperiment(searchList, experimentList -> {
            allExpDataList = experimentList;
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

    /**
     * Check to see if an experiment id already exists
     * @param exp an experiment
     * @return boolean if experiment id already exists
     */
    private boolean alreadyExists(Experiment exp) {
        for(AllExperimentListItem expItem : allExperimentListItems) {
            if(expItem.getExperiment().getExperimentID().equals(exp.getExperimentID())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Update subscriptions (buttons)
     */
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

    /**
     * Removes unpublished experiments for allExpDataList
     * If experiment is unpublished but user is owner, it is excluded
     */
    private void unpublishedCheck() {
        List<Experiment> tempList = new ArrayList<>();
        for(Experiment exp : allExpDataList) {
            if(exp.isUnpublished() && !thisUser.getUid().equals(exp.getOwnerID())) {
                tempList.add(exp);
            }
        }
        allExpDataList.removeAll(tempList);
    }

    /**
     * Update the view
     */
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
                        unpublishedCheck();
                        updateSubs();
                        allExpAdapter = new CustomListAllExperiments(thisContext, allExperimentListItems, listener);
                        allExpView = getView().findViewById(R.id.all_experiment_list);
                        allExpView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                        allExpView.setAdapter(allExpAdapter);
                        allExpView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String expID = allExperimentListItems.get(position).getExperiment().getExperimentID();
                                openFragmentWithExperimentIDAndUser(QuestionsFragment.newInstance(), user.getUid(), expID);
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * opens a fragment and bundles uid and experiment id
     * @param fragment fragment to open
     * @param uid User ID
     * @param experimentID Experiment ID
     */
    private void openFragmentWithExperimentIDAndUser(Fragment fragment, String uid, String experimentID) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ExperimentID", experimentID);
        bundle.putSerializable("UserId",uid);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, "Questions");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        // change the title of the app bar
        ( (MainActivity) getActivity()).setActionBarTitle("CrowderApp");
    }

}
