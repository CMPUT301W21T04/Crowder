package com.example.crowderapp.models;

import java.util.Date;

public class MeasurementExperiment extends Experiment {

    private double averageMeasurement;
    private int measurementCount;

    public MeasurementExperiment() {
        super();
    }

    public MeasurementExperiment(String experimentID, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, int ownerID) {
        super(experimentID, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    public void addMeasurement(double meas, String experimenter) {
        averageMeasurement = (averageMeasurement*measurementCount+meas)/(1+measurementCount);
        measurementCount += 1;
        trials.add(new MeasurementTrial(experimenter, new Date(), meas));
    }
}
