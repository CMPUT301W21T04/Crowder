package com.example.crowderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.views.AddExperimentFragment;
import com.example.crowderapp.views.AllExperimentsFragment;
import com.example.crowderapp.views.MyExperimentsFragment;
import com.example.crowderapp.views.ProfileFragment;
import com.example.crowderapp.views.trialfragments.BinomialTrialFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
        implements AddExperimentFragment.OnFragmentInteractionListener{

    BottomNavigationView bottomNavigation;
    Toolbar toolbar;
    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        fab = findViewById(R.id.add_experiment_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddExperimentFragment().show(getSupportFragmentManager(), "ADD_EXPR");
            }
        });

        openFragment(AllExperimentsFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.location_item:
//                Toast.makeText(this, "yo", Toast.LENGTH_SHORT).show();
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_all_experiments:
                            fab.show();
                            openFragment(AllExperimentsFragment.newInstance());
                            return true;
                        case R.id.navigation_my_experiments:
                            fab.hide();
                            openFragment(MyExperimentsFragment.newInstance());
                            return true;
                        case R.id.navigation_profile:
                            fab.hide();
                            openFragment(ProfileFragment.newInstance());
                            return true;
                    }
                    return false;
                }
            };

    @Override
    public void onOkPressed() {

    }
}