package com.example.crowderapp.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;

public class MyExperimentsFragment extends Fragment {

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_experiments_fragment, container, false);
    }
}
