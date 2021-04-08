package com.example.crowderapp.models.adapters;

import com.example.crowderapp.models.Location;

/**
 * Adapts the android/google API's location object to
 * our location object.
 */
public class LocationAdapter extends Location {

    transient android.location.Location wrapped;

    public LocationAdapter(android.location.Location wrappee) {
        this.wrapped = wrappee;
    }

    @Override
    public double getLongitude() {
        return wrapped.getLongitude();
    }

    @Override
    public void setLongitude(double longitude) {
        wrapped.setLongitude(longitude);
    }

    @Override
    public double getLatitude() {
        return wrapped.getLatitude();
    }

    @Override
    public void setLatitude(double latitude) {
        wrapped.getLatitude();
    }
}
