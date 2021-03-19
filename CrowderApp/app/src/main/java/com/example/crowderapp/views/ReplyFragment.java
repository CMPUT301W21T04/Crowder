package com.example.crowderapp.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.CommentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.models.CustomListQuestions;
import com.example.crowderapp.models.CustomListReply;
import com.example.crowderapp.models.posts.Question;
import com.example.crowderapp.models.posts.Reply;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ReplyFragment extends Fragment {

    private List<Reply> replyList = new ArrayList<>();
    private ListView repliesView;
    private ArrayAdapter<Reply> replyAdapter;
    private Question question;
    private String userID;
    private FloatingActionButton fab;
    private CommentHandler commentHandler = new CommentHandler();
    private UserHandler userHandler;

    public ReplyFragment() {

    }

    public static ReplyFragment newInstance() {
        ReplyFragment fragment = new ReplyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void update() {
        replyList.clear();
        replyList.addAll(question.getReplyList());
        replyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        question = (Question) bundle.getSerializable("Question");
        userID = bundle.getString("UserID");

    }



    private void openFragment(Fragment fragment, Question question) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reply_fragment, container, false);

        fab = view.findViewById(R.id.add_reply_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddReplyFragment().newInstance(question, userID).show(getFragmentManager(), "ADD_REPLY");
            }
        });

        replyList = question.getReplyList();
//        Reply reply = new Reply("Yes it does", "76S5iUPzQ1Lhlmh819nv", question.getCommentId());
//        replyList.add(reply);
        replyAdapter = new CustomListReply(getContext(), replyList);
        repliesView = view.findViewById(R.id.reply_list);
        repliesView.setAdapter(replyAdapter);

        return view;
    }
}