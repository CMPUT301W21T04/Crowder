package com.example.crowderapp.views.trialfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;

public class BinomialTrialFragment extends Fragment {

    public BinomialTrialFragment() {

    }

    public static BinomialTrialFragment newInstance() {
        BinomialTrialFragment fragment = new BinomialTrialFragment();
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
        return inflater.inflate(R.layout.binomial_trial_fragment, container, false);
    }
}
