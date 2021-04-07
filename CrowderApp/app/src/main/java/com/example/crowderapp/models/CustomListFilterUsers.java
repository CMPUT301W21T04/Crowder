package com.example.crowderapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowderapp.R;
import com.example.crowderapp.models.posts.Question;

import java.util.List;

public class CustomListFilterUsers extends ArrayAdapter<UserFilterListItem> {
    private List<UserFilterListItem> userItems;
    private Context context;
    private CompoundButton.OnCheckedChangeListener listener;

    /**
     * Constructor
     * @param context context
     * @param users List of questions to show
     */
    public CustomListFilterUsers(Context context, List<UserFilterListItem> users, CompoundButton.OnCheckedChangeListener listener) {
        super(context, 0, users);
        this.context = context;
        this.userItems = users;
        this.listener = listener;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.user_filter_custom_list, parent, false);
        }

        UserFilterListItem userItem = userItems.get(position);
        String user = userItem.getUser();

        TextView userName = view.findViewById(R.id.filterUserNameTextView);
        CheckBox included = view.findViewById(R.id.includedCheckbox);
        included.setOnCheckedChangeListener(listener);
        included.setTag(position);

        userName.setText(user);
        if(userItem.getIsChecked()) {
            included.setChecked(true);
        }
        else {
            included.setChecked(false);
        }

        return view;
    }
}

