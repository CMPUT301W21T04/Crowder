package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.ScanObj;
import com.google.android.gms.tasks.Task;

/**
 * DAO used to access scan objects for an experiment.
 */
public abstract class ScanObjectDAO {

    /**
     * Creates entry of scan object on DB if it doesn't exist.
     * Updates the document if it does exist.
     * scanObj with no keys will automatically have a key generated for them.
     * @param scanObj The can object to add to db.
     * @return Task with scan object ID
     */
    public abstract Task<String> createUpdateScanObj(ScanObj scanObj);

    /**
     * Gets the scan object based on the key.
     * @param key The key of the scanned object.
     * @param expId The experiment we are interested in.
     * @return Task with Scan object.
     */
    public abstract Task<ScanObj> getScanObj(String key, String expId);
}
