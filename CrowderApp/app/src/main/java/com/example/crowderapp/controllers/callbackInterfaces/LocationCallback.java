package com.example.crowderapp.controllers.callbackInterfaces;

import com.example.crowderapp.models.Location;

public interface LocationCallback {
    /**
     * Callback for when a location is received.
     * @param location The location of the device or null if failed to get the location.
     */
    void callbackResult(Location location);
}
