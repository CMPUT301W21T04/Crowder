package com.example.crowderapp.views;


import androidx.fragment.app.DialogFragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.CommentHandler;
import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.controllers.callbackInterfaces.addQuestionToExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.createExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getUserByIDCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.User;
import com.example.crowderapp.models.posts.Question;

public class AddQuestionFragment extends DialogFragment {


    private UserHandler userHandler;
    private ExperimentHandler handler = new ExperimentHandler();
    EditText newQuestionField;
    String newQuestion;
    private CommentHandler commentHandler = new CommentHandler();
    String experimentID;
    String userID;

    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    static AddQuestionFragment newInstance(String experimentID, String uid) {
        Bundle args = new Bundle();
        args.putSerializable("ExperimentID", experimentID);
        args.putSerializable("UserId", uid);
        AddQuestionFragment fragment = new AddQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        experimentID = args.getString("ExperimentID");
        userID = args.getString("UserId");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_question_fragment, null);

        userHandler = new UserHandler(getActivity().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));

        newQuestionField = view.findViewById(R.id.question_EditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Question")
                .setIcon(R.drawable.ic_baseline_mode_comment_24)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }
    // The use of onResume to be able to override the automatic dismiss was learned from
    // StackOverflow, https://stackoverflow.com/
    // an answer by Sogger on Mar 25 '13 at 15:48
    // https://stackoverflow.com/users/579234/sogger
    // to question "How to prevent a dialog from closing when a button is clicked"
    // https://stackoverflow.com/questions/2620444/how-to-prevent-a-dialog-from-closing-when-a-button-is-clicked
    // under CC-BY-SA
    @Override
    public void onResume() {
        super.onResume();
        AlertDialog ad = (AlertDialog) getDialog();
        if(ad == null) {
            return;
        }
        Button posButton = ad.getButton(Dialog.BUTTON_POSITIVE);
        posButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuestion = newQuestionField.getText().toString();
                if(newQuestion.matches("")) {
                    Toast.makeText(getContext(), "Invalid Question", Toast.LENGTH_LONG);
                } else {
                    commentHandler.addQuestionToExperiment(experimentID, new Question(newQuestionField.getText().toString(), userID), new addQuestionToExperimentCallBack() {
                        @Override
                        public void callBackResult(String questionID) {
                            Log.v(String.valueOf(questionID), "Question has been added");
                            listener.onOkPressed();
                            ad.dismiss();
                        }
                    });
                }
            }
        });

    }




}
