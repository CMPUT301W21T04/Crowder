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
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentQuestionsCallBack;
import com.example.crowderapp.models.CustomListQuestions;
import com.example.crowderapp.models.posts.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsFragment extends Fragment {

    private List<Question> questionsList = new ArrayList<Question>();
    private ListView questionsView;
    private ArrayAdapter<Question> questionAdapter;
    private ExperimentHandler expHandler = new ExperimentHandler();
    private CommentHandler commentHandler = new CommentHandler();
    private String experimentId;
    private Context thisContext;

    public QuestionsFragment() {}

    public static QuestionsFragment newInstance() {
        QuestionsFragment fragment = new QuestionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        experimentId = (String) bundle.getSerializable("ExperimentID");

        commentHandler.getExperimentQuestions(experimentId, new getExperimentQuestionsCallBack() {
            @Override
            public void callBackResult(List<Question> questionList) {
                questionsList = questionList;

                questionAdapter = new CustomListQuestions(thisContext, questionsList);
                questionsView = getView().findViewById(R.id.question_list);
                questionsView.setAdapter(questionAdapter);
                questionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openFragment(ReplyFragment.newInstance(), questionAdapter.getItem(position));
                    }
                });
            }
        });
    }

    private void openFragment(Fragment fragment, Question question) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Question", question);
        fragment.setArguments(bundle);
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
