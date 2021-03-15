package com.example.crowderapp.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.models.User;

public class ProfileFragment extends Fragment {

    UserHandler userHandler;

    TextView phoneBox;
    TextView emailBox;
    TextView usernameBox;

    User user;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        emailBox = view.findViewById(R.id.profile_email);
        phoneBox = view.findViewById(R.id.profile_phone);
        usernameBox = view.findViewById(R.id.profile_username);

        userHandler.getCurrentUser().addOnSuccessListener(user -> {
            emailBox.setText(user.getEmail());
            phoneBox.setText(user.getPhone());
            usernameBox.setText(user.getUid());
        });

        return view;
    }
}
