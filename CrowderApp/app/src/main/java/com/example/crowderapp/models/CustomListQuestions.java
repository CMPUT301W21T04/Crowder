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
import com.example.crowderapp.models.posts.Question;

import java.util.List;

public class CustomListQuestions extends ArrayAdapter<Question> {
    private List<Question> questions;
    private Context context;

    public CustomListQuestions(Context context, List<Question> questions) {
        super(context, 0, questions);
        this.context = context;
        this.questions = questions;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.my_experiments_custom_list, parent, false);
        }

        Question question = questions.get(position);
        TextView questionText = view.findViewById(R.id.question_TextView);
        questionText.setText(question.getBody());


        return view;
    }
}

