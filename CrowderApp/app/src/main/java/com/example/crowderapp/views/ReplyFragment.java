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
import com.example.crowderapp.models.CustomListQuestions;
import com.example.crowderapp.models.CustomListReply;
import com.example.crowderapp.models.posts.Question;
import com.example.crowderapp.models.posts.Reply;

import java.util.ArrayList;
import java.util.List;

public class ReplyFragment extends Fragment {

    private List<Reply> replyList = new ArrayList<>();
    private ListView repliesView;
    private ArrayAdapter<Reply> replyAdapter;
    private Question question;
    private CommentHandler commentHandler = new CommentHandler();
    private Context thisContext;

    public ReplyFragment() {

    }

    public static ReplyFragment newInstance() {
        ReplyFragment fragment = new ReplyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        question = (Question) bundle.getSerializable("Question");

        replyList = question.getReplyList();
        replyAdapter = new CustomListReply(thisContext, replyList);
        repliesView.setAdapter(replyAdapter);

    }

    private void openFragment(Fragment fragment, Question question) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisContext = container.getContext();
        View view = inflater.inflate(R.layout.questions_fragment, container, false);
        return view;
    }
}