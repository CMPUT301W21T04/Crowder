package com.example.crowderapp.models;

import android.location.Location;

import java.util.Date;

public class MeasurementExperiment extends Experiment {

    private double averageMeasurement;
    private int measurementCount;

    public MeasurementExperiment() {
        super();
    }

    public MeasurementExperiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        super(experimentID, name, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    public void addMeasurement(double meas, String experimenter, Location location) {
        averageMeasurement = (averageMeasurement*measurementCount+meas)/(1+measurementCount);
        measurementCount += 1;
        trials.add(new MeasurementTrial(experimenter, new Date(), meas, location, this.getExperimentID()));
    }
}
