package com.example.crowderapp.views.trialfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;
import com.example.crowderapp.views.AllExperimentsFragment;

public class NonNegativeCountTrialFragment extends Fragment {

    public NonNegativeCountTrialFragment() {

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_experiments_fragment, container, false);
    }
}
