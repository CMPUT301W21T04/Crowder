package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeasurementExperiment extends Experiment {

    private double averageMeasurement;
    private int measurementCount;
    protected List<MeasurementTrial> trials = new ArrayList<MeasurementTrial>();

    public MeasurementExperiment() {
        super();
    }

    public MeasurementExperiment(String experimentID, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, int ownerID) {
        super(experimentID, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    @Override
    public List<? extends Trial> getTrials() {
        return trials;
    }

    @Override
    public ExperimentStats getStats() {
        return new MeasurementStats(trials);
    }

    public void addMeasurement(double meas, String experimenter) {
        averageMeasurement = (averageMeasurement*measurementCount+meas)/(1+measurementCount);
        measurementCount += 1;
        trials.add(new MeasurementTrial(experimenter, new Date(), meas));
    }
}
