package com.example.crowderapp.models;

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

    /**
     * Adds a measurement to the trials and computes new average
     * @param meas measurement to be added
     * @param experimenter unique id of experimenter adding the trial
     * @param location location where the trial was taken
     */
    public void addMeasurement(double meas, String experimenter, Location location) {
        averageMeasurement = (averageMeasurement*measurementCount+meas)/(1+measurementCount);
        measurementCount += 1;
        trials.add(new MeasurementTrial(experimenter, new Date(), meas, location, this.getExperimentID()));
    }

    /**
     * @return The average of all the real measurements.
     */
    public double getAverageMeasurement() {
        return averageMeasurement;
    }

    /**
     * @param averageMeasurement
     */
    public void setAverageMeasurement(double averageMeasurement) {
        this.averageMeasurement = averageMeasurement;
    }

    /**
     * @return How many measurements have been made.
     */
    public int getMeasurementCount() {
        return measurementCount;
    }

    /**
     * Directly set the number of measurements made.
     * @param measurementCount
     */
    public void setMeasurementCount(int measurementCount) {
        this.measurementCount = measurementCount;
    }
}
