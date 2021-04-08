package com.example.crowderapp.controllers;

import android.util.Log;

import com.example.crowderapp.controllers.callbackInterfaces.ScanObjectCallback;
import com.example.crowderapp.models.ScanObj;
import com.example.crowderapp.models.dao.ScanObjectDAO;
import com.example.crowderapp.models.dao.ScanObjectFSDAO;
import com.google.android.gms.tasks.Task;

public class ScanObjHandler {

    private static final String TAG = "ScanObjHandler";

    private String experimentId;

    private ScanObjectDAO dao;

    /**
     * @param experiment Experiment associated with al further operations.
     */
    public ScanObjHandler(String experiment) {
        this(experiment, new ScanObjectFSDAO());
    }

    public ScanObjHandler(String experiment, ScanObjectDAO dao) {
        if (experiment == null) {
            throw new IllegalArgumentException("Experiment cannot be null");
        }
        experimentId = experiment;
        this.dao = dao;
    }

    public void setExperiment(String experiment) {
        this.experimentId = experiment;
    }

    /**
     * Creates a scan object with a DB generated key.
     * Used for QR codes.
     * @param value The command/value of the scan.
     * @param cb The callback.
     */
    public void createScanObj(String value, ScanObjectCallback cb) {
        ScanObj scanObj = new ScanObj();
        scanObj.setexpID(experimentId);
        scanObj.setKey(null);
        scanObj.setValue(value);

        dao.createUpdateScanObj(scanObj).addOnSuccessListener(s -> {
            scanObj.setKey(s); // Set to generated key
            cb.callback(scanObj);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "createScanObj: Failed to create a scan object", e);
        });
    }

    /**
     * Creates a scan object with an applicaiton provided key.
     * Use for barcodes.
     * If barcode already exists for experiment, it will ovewrite it.
     * @param key The barcode code.
     * @param value The command/value of the scan.
     * @param cb Callback
     */
    public void createScanObj(String key, String value, ScanObjectCallback cb) {
        ScanObj scanObj = new ScanObj();
        scanObj.setexpID(experimentId);
        scanObj.setKey(key);
        scanObj.setValue(value);

        dao.createUpdateScanObj(scanObj).addOnSuccessListener(s -> {
            // Key already in scanobject
            cb.callback(scanObj);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "createScanObj: Failed to create a scan object", e);
        });
    }

    /**
     * Gets the scan object by key.
     * @param key The key of scan object.
     * @param cb Callback
     */
    public void getScanObj(String key, ScanObjectCallback cb) {
        dao.getScanObj(key, experimentId).addOnSuccessListener(scanObj -> {
            cb.callback(scanObj);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "getScanObj: Failed to get the scan object with key " + key, e);
        });
    }
}
