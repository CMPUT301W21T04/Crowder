package com.example.crowderapp.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.models.User;

public class ProfileFragment extends Fragment {

    UserHandler userHandler;

    EditText phoneBox;
    EditText emailBox;
    EditText usernameBox;
    ImageButton editBtn;

    Drawable editImage;
    Drawable checkImage;

    boolean editMode = false;

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
        editImage = ContextCompat.getDrawable(getActivity(), com.google.android.material.R.drawable.material_ic_edit_black_24dp);
        checkImage = ContextCompat.getDrawable(getActivity(), com.google.android.material.R.drawable.ic_mtrl_checked_circle);

        userHandler = new UserHandler(getActivity().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        emailBox = view.findViewById(R.id.profile_email);
        phoneBox = view.findViewById(R.id.profile_phone);
        usernameBox = view.findViewById(R.id.profile_username);
        editBtn = view.findViewById(R.id.profile_edit);

        // Set icon to pencil first
        editBtn.setImageDrawable(editImage);

        // Can't edit to begin with
        emailBox.setEnabled(false);
        phoneBox.setEnabled(false);
        usernameBox.setEnabled(false);

        editBtn.setOnClickListener(v -> {
            if (!editMode) {
                // Turn on Edit mode
                editBtn.setImageDrawable(checkImage);
                emailBox.setEnabled(true);
                phoneBox.setEnabled(true);
                usernameBox.setEnabled(true);

            } else  {
                // Turn off edit mode.
                editBtn.setImageDrawable(editImage);
                emailBox.setEnabled(false);
                phoneBox.setEnabled(false);
                usernameBox.setEnabled(false);

                user.setName(usernameBox.getText().toString());
                user.setPhone(phoneBox.getText().toString());
                user.setEmail(emailBox.getText().toString());

                userHandler.updateCurrentUser(user);
            }

            editMode = !editMode;
        });

        userHandler.observeCurrentUser(getActivity(), (dao, user) -> {
            emailBox.setText(user.getEmail());
            phoneBox.setText(user.getPhone());
            usernameBox.setText(user.getName());
            this.user = user;
        });

        return view;
    }
}
