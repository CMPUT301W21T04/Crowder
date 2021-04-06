package com.example.crowderapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.controllers.callbackInterfaces.getUserByIDCallBack;
import com.example.crowderapp.models.posts.Reply;

import java.util.List;

public class CustomListReply extends ArrayAdapter<Reply> {
    private List<Reply> replies;
    private Context context;
    public UserHandler userHandler;
    private User user;
    private OnUsernameClickListener userNameClickCb;

    /**
     * Callback interface when the username is clicked.
     */
    public interface OnUsernameClickListener {
        void onClick(String userId);
    }

    /**
     * Constructor
     * @param context context
     * @param replies List of replies to show
     */
    public CustomListReply(Context context, List<Reply> replies) {
        super(context, 0, replies);
        this.context = context;
        this.replies = replies;
        userNameClickCb = null;
    }

    /**
     * Set the listener for when username is clicked.
     * @param cb
     */
    public void setOnUsernameClickListener(OnUsernameClickListener cb) {
        userNameClickCb = cb;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.reply_list, parent, false);
        }
        userHandler = new UserHandler(getContext().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));
        Reply reply = replies.get(position);
        TextView replyText = view.findViewById(R.id.reply_TextView);
        TextView userText = view.findViewById(R.id.username_TextView);
        replyText.setText(reply.getBody());
        userHandler.getUserByID(reply.getUserId(), new getUserByIDCallBack() {
            @Override
            public void callBackResult(User user) {
                userText.setText(user.getName());

                userText.setOnClickListener(v -> {
                    if (userNameClickCb != null) {
                        userNameClickCb.onClick(reply.getUserId());
                    }
                });
            }
        });

        return view;
    }
}
