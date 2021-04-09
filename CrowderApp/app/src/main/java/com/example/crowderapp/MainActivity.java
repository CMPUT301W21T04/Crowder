package com.example.crowderapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.crowderapp.controllers.LocationHandler;
import com.example.crowderapp.models.AllExperimentListItem;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.posts.Question;
import com.example.crowderapp.views.AddExperimentFragment;
import com.example.crowderapp.views.AddQuestionFragment;
import com.example.crowderapp.views.AllExperimentsFragment;
import com.example.crowderapp.views.BinomialBarcodeFragment;
import com.example.crowderapp.views.BinomialQRFragment;
import com.example.crowderapp.views.LocationPopupFragment;
import com.example.crowderapp.views.MeasurementBarcodeFragment;
import com.example.crowderapp.views.MeasurementQRFragment;
import com.example.crowderapp.views.MyExperimentsFragment;
import com.example.crowderapp.views.NonNegBarcodeFragment;
import com.example.crowderapp.views.NonNegQRFragment;
import com.example.crowderapp.views.ProfileFragment;
import com.example.crowderapp.views.QuestionsFragment;
import com.example.crowderapp.views.ReplyFragment;
import com.example.crowderapp.views.trialfragments.TrialFragment;
import com.example.crowderapp.views.trialfragments.BinomialTrialFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// GPS Permission Code Based on Android Developmer Documentation
// At: https://developer.android.com/training/permissions/requesting
// Licensed under Apache 2.0

public class MainActivity extends AppCompatActivity
        implements AddExperimentFragment.OnFragmentInteractionListener,
        AddQuestionFragment.OnFragmentInteractionListener,
        LocationPopupFragment.OnFragmentInteractionListener,
        BinomialBarcodeFragment.OnFragmentInteractionListener,
        NonNegBarcodeFragment.OnFragmentInteractionListener,
        MeasurementBarcodeFragment.OnFragmentInteractionListener,
        BinomialQRFragment.OnFragmentInteractionListener,
        NonNegQRFragment.OnFragmentInteractionListener,
        MeasurementQRFragment.OnFragmentInteractionListener{

    BottomNavigationView bottomNavigation;
    Toolbar toolbar;
    AllExperimentsFragment allExpFrag;

    // Permissions dialogue
    ActivityResultLauncher<String> permissionsDialogue;

    // Constant Strings
    final private static String LOCATION_DENIED = "Location permission is needed for location experiments.";

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

        // Set up permissions dialogue for GPS
        permissionsDialogue = registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
            if (!granted) {
                Toast.makeText(this, LOCATION_DENIED, Toast.LENGTH_LONG);
            }
        });

        // Ask for GPS permissions.
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // No need for action.
        }
        else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Toast.makeText(this, LOCATION_DENIED, Toast.LENGTH_LONG).show();
        }
        else {
            // Request permission
            permissionsDialogue.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }


    public void openFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        if(fm.getBackStackEntryCount() != 0) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    // https://androidwave.com/bottom-navigation-bar-android-example/
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    setActionBarTitle("CrowderApp");
                    switch (item.getItemId()) {
                        case R.id.navigation_all_experiments:
                            allExpFrag = AllExperimentsFragment.newInstance();
                            openFragment(allExpFrag);
                            return true;
                        case R.id.navigation_my_experiments:
                            openFragment(MyExperimentsFragment.newInstance());
                            return true;
                        case R.id.navigation_profile:
                            openFragment(ProfileFragment.newInstance());
                            return true;
                    }
                    return false;
                }
            };


    public void setActionBarTitle(String title){
        toolbar.setTitle(title);
    }


    // onOkPressed Methods
    @Override
    public void onOkPressed(boolean allow) {
        if(!allow) {
            openFragment(MyExperimentsFragment.newInstance());
            return;
        }
    }

    @Override
    public void onOkPressed(Question question) {
        ReplyFragment replyFragment = (ReplyFragment) getSupportFragmentManager().findFragmentByTag("Replies");
        replyFragment.update(question);
    }

    @Override
    public void onOkPressed() {
        QuestionsFragment questionFrag = (QuestionsFragment) getSupportFragmentManager().findFragmentByTag("Questions");
        if(questionFrag != null && questionFrag.isVisible()) {
            questionFrag.update();
            return;
        }
        openFragment(AllExperimentsFragment.newInstance());
    }




}
