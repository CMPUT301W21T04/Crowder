package com.example.crowderapp.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.callbackInterfaces.getUserByIDCallBack;
import com.example.crowderapp.models.User;
import com.example.crowderapp.models.dao.UserDAO;

/**
 * Shows information for a current user.
 * Unlike the normal ProfileFragment, there are no options to edit the user
 * info in this fragment.
 */
public class ImmutableProfileFragment extends ProfileFragment {

    private final static String USER_ID = "USER_ID";

    /**
     * Creates the profile view with a specific user.
     * @param userId
     * @return
     */
    public static ImmutableProfileFragment newInstance(String userId) {
        ImmutableProfileFragment fragment = new ImmutableProfileFragment();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Create the profile view for a specific user.
     * @return
     */
    public static ImmutableProfileFragment newInstance() {
        ImmutableProfileFragment fragment = new ImmutableProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        attachViews(view);

        // Set edit button to not appear
        editBtn.setVisibility(View.INVISIBLE);

        // Can't edit fields.
        emailBox.setEnabled(false);
        phoneBox.setEnabled(false);
        usernameBox.setEnabled(false);

        Bundle bundle = getArguments();
        String userId = bundle.getString(USER_ID);

        // Observer to update the fields
        getUserByIDCallBack obs = new getUserByIDCallBack() {
            @Override
            public void callBackResult(User receive_user) {
                emailBox.setText(receive_user.getEmail());
                phoneBox.setText(receive_user.getPhone());
                usernameBox.setText(receive_user.getName());
                user = receive_user;
            }
        };

        if (userId == null) {
            // Show current user.
            userHandler.getCurrentUser(obs);
        }
        else {
            // Show the chosen user
            userHandler.getUserByID(userId, obs);
        }

        return view;
    }
}
