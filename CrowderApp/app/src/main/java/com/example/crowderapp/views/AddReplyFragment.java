package com.example.crowderapp.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.crowderapp.R;
import com.example.crowderapp.controllers.CommentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.addQuestionToExperimentCallBack;
import com.example.crowderapp.models.posts.Question;
import com.example.crowderapp.models.posts.Reply;

public class AddReplyFragment extends DialogFragment {

    private Question question;
    private String userID;
    private EditText newReplyField;
    private String newReply;
    private CommentHandler commentHandler = new CommentHandler();

    private AddQuestionFragment.OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddQuestionFragment.OnFragmentInteractionListener){
            listener = (AddQuestionFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    static AddReplyFragment newInstance(Question question, String uid) {
        Bundle args = new Bundle();
        args.putSerializable("Question", question);
        args.putSerializable("UserID", uid);
        AddReplyFragment fragment = new AddReplyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        question = (Question) args.getSerializable("Question");
        userID = args.getString("UserID");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_reply_fragment, null);

        newReplyField = view.findViewById(R.id.reply_EditText);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Reply")
                .setIcon(R.drawable.ic_baseline_mode_comment_24)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog ad = (AlertDialog) getDialog();
        if (ad == null) {
            return;
        }
        Button posButton = ad.getButton(Dialog.BUTTON_POSITIVE);
        posButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newReply = newReplyField.getText().toString();
                question.addReply(new Reply(newReply, userID, question.getCommentId()));
                if (newReply.matches("")) {
                    Toast.makeText(getContext(), "Invalid Question", Toast.LENGTH_LONG);
                } else {
                    commentHandler.updateQuestionForExperiment(question.getCommentId(), question);
                    listener.onOkPressed();
                    ad.dismiss();
                }
            }
        });
    }
}
