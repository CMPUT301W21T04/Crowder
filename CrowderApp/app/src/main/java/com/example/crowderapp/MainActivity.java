package com.example.crowderapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.crowderapp.models.AllExperimentListItem;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.views.AddExperimentFragment;
import com.example.crowderapp.views.AddQuestionFragment;
import com.example.crowderapp.views.AllExperimentsFragment;
import com.example.crowderapp.views.MyExperimentsFragment;
import com.example.crowderapp.views.ProfileFragment;
import com.example.crowderapp.views.QuestionsFragment;
import com.example.crowderapp.views.ReplyFragment;
import com.example.crowderapp.views.trialfragments.BinomialTrialFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
        implements AddExperimentFragment.OnFragmentInteractionListener,
        AddQuestionFragment.OnFragmentInteractionListener{

    BottomNavigationView bottomNavigation;
    Toolbar toolbar;
    public FloatingActionButton fab;
    AllExperimentsFragment allExpFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);



        allExpFrag = AllExperimentsFragment.newInstance();
        openFragment(allExpFrag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }


    public void openFragment(Fragment fragment) {
        // https://stackoverflow.com/questions/6186433/clear-back-stack-using-fragments
        FragmentManager fm = getSupportFragmentManager();
//        for(int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
//            fm.popBackStack();
//        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        if(!(fragment instanceof MyExperimentsFragment) || !(fragment instanceof ProfileFragment))
            if(fm.getBackStackEntryCount() != 0) {
                transaction.addToBackStack(null);
            }
        transaction.commit();
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_all_experiments:
//                            fab.show();
                            allExpFrag = AllExperimentsFragment.newInstance();
                            openFragment(allExpFrag);
                            return true;
                        case R.id.navigation_my_experiments:
//                            fab.hide();
                            openFragment(MyExperimentsFragment.newInstance());
                            return true;
                        case R.id.navigation_profile:
//                            fab.hide();
                            openFragment(ProfileFragment.newInstance());
                            return true;
                    }
                    return false;
                }
            };

    @Override
    public void onOkPressed() {
//        allExpFrag.update();
        QuestionsFragment questionFrag = (QuestionsFragment) getSupportFragmentManager().findFragmentByTag("Questions");
        ReplyFragment replyFragment = (ReplyFragment) getSupportFragmentManager().findFragmentByTag("Replies");
        if(questionFrag != null && questionFrag.isVisible()) {
            questionFrag.update();
            return;
        }
        else if (replyFragment != null && replyFragment.isVisible()) {
            replyFragment.update();
            return;
        }
        openFragment(AllExperimentsFragment.newInstance());
    }

}
