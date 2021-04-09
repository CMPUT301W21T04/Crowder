package com.example.crowderapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentCallBack;
import com.example.crowderapp.models.Experiment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.List;

// Code started from Template Google Map Activity Class
public class HeatmapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Experiment mExperiment;
    private ExperimentHandler handler = new ExperimentHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmap);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = this.getIntent();
        mExperiment = (Experiment) intent.getSerializableExtra("experiment");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        handler.refreshExperimentTrials(mExperiment, new getExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {
                List<LatLng> latLngs;
                mExperiment = experiment;
                latLngs = handler.getLatLongExperiment(mExperiment);

                // Put no overlay when no latLngs
                if(latLngs.size() == 0) {
                    return;
                }

                // Display Heatmap on map
                HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                        .data(latLngs)
                        .build();
                TileOverlay overlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngs.get(0)));
            }
        });

    }
}
