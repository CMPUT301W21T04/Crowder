package com.example.crowderapp.views;

import android.app.Activity;
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

/**
 * Fragment to show the current user info.
 * Should never be used with non local user as this allows information
 * to be edited.
 */
public class ProfileFragment extends Fragment {

    protected UserHandler userHandler;
    protected User user;

    protected EditText phoneBox;
    protected EditText emailBox;
    protected EditText usernameBox;
    protected ImageButton editBtn;

    protected Drawable editImage;
    protected Drawable checkImage;

    protected boolean editMode = false;

    protected Activity parentActivity;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /**
     * Create references to all the views in a parent view/fragment.
     * @param view The view representing the fragment.
     */
    protected void attachViews(View view) {
        emailBox = view.findViewById(R.id.profile_email);
        phoneBox = view.findViewById(R.id.profile_phone);
        usernameBox = view.findViewById(R.id.profile_username);
        editBtn = view.findViewById(R.id.profile_edit);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        attachViews(view);

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
                // User entered new info
                String phone = phoneBox.getText().toString();
                String email = emailBox.getText().toString();
                
                if (!User.validPhoneNumber(phone)) {
                    Toast.makeText(getActivity(), "Phone must be in 111-222-3333 format", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!User.validEmail(email)) {
                    Toast.makeText(getActivity(), "Email is not valid.", Toast.LENGTH_LONG).show();
                    return;
                }
                
                // Turn off edit mode.
                editBtn.setImageDrawable(editImage);
                emailBox.setEnabled(false);
                phoneBox.setEnabled(false);
                usernameBox.setEnabled(false);

                user.setName(usernameBox.getText().toString());
                user.setPhone(phone);
                user.setEmail(email);

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
