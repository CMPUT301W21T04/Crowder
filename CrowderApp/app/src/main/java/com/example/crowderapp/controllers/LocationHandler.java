package com.example.crowderapp.controllers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.crowderapp.controllers.callbackInterfaces.LocationCallback;
import com.example.crowderapp.models.adapters.LocationAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

/**
 * Handler for location related services.
 */
public class LocationHandler {

    final private static String TAG = "LocationHandler";

    Context context;
    FusedLocationProviderClient fusedLocationClient;

    /**
     * Constructor
     * @param context The activity calling creating the handler.
     */
    public LocationHandler(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    /**
     * For dependency injection.
     * @param context
     * @param provider
     */
    public LocationHandler(Context context, FusedLocationProviderClient provider) {
        this.context = context;
        this.fusedLocationClient = provider;
    }

    /**
     * Check if this app has necessary GPS permissions.
     * @return True if GPS permission was granted.
     */
    public boolean hasGPSPermissions() {
        return this.context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Get's the current location of the phone.
     * Make sure that you have permission to access GPS before calling this.
     * @param cb The callback after retrieving the location.
     */
    @SuppressLint("MissingPermission") // The failure listener will handle this error for us.
    public void getCurrentLocation(LocationCallback cb) {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            cb.callbackResult(new LocationAdapter(location));
        }).addOnFailureListener(e -> {

            if (e instanceof SecurityException) {
                // Directly rethrow security exception instead of silencing it
                throw (SecurityException) e;
            }

            Log.e(TAG, "getCurrentLocation: Failed to get Location.", e);
            cb.callbackResult(null);
        });
    }
}
