package com.example.crowderapp.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.models.CustomListFilterUsers;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.UserFilterListItem;

import java.util.ArrayList;
import java.util.List;

public class UserFilterFragment extends DialogFragment {
    private ListView userListView;
    private ArrayAdapter<UserFilterListItem> userAdapter;
    private List<UserFilterListItem> userDataList = new ArrayList<UserFilterListItem>();
    private ExperimentHandler handler = new ExperimentHandler();
    private Experiment currentExp;

    public UserFilterFragment() {

    }

    public static UserFilterFragment newInstance(Experiment exp) {
        Bundle args = new Bundle();
        args.putSerializable("Experiment", exp);
        UserFilterFragment fragment = new UserFilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void populateList(Experiment exp) {
        List<String> experimentUsers = exp.getExperimentUsers();
        List<String> excludedUsers = exp.getExcludedUsers();
        for(String u : experimentUsers) {
            Log.v("Adding: ", u);
            if(!excludedUsers.contains(u)) {
                userDataList.add(new UserFilterListItem(u, true));
            }
            else {
                userDataList.add(new UserFilterListItem(u, false));
            }
        }
    }

    public List<String> getExcludedList() {
        List<String> excludes = new ArrayList<String>();
        for(int i=0; i < userAdapter.getCount(); i++) {
            UserFilterListItem item = userAdapter.getItem(i);
            if(!item.getIsChecked()) { // Not selected to be included
                excludes.add(item.getUser());
            }
        }
        return excludes;
    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = (int) buttonView.getTag();
            if(isChecked) {
                userDataList.set(position, new UserFilterListItem(userDataList.get(position).getUser(), true));
            }
            else {
                userDataList.set(position, new UserFilterListItem(userDataList.get(position).getUser(), false));
            }
        }
    };

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        currentExp = (Experiment) args.getSerializable("Experiment");

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.user_filter_fragment, null);
        populateList(currentExp);
        userAdapter = new CustomListFilterUsers(getContext(), userDataList, listener);
        userListView = view.findViewById(R.id.filter_user_list);
        userListView.setAdapter(userAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("User Filter")
                .setIcon(R.drawable.ic_baseline_mode_comment_24)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get list and set excluded list
                        currentExp.setExcludedUsers(getExcludedList());
                        handler.updateExperiment(currentExp);
                        dismiss();
                    }
                }).create();
    }

}
