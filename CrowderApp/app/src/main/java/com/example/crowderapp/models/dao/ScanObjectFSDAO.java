package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.ScanObj;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Firestore implementation for ScanObjectDAO
 */
public class ScanObjectFSDAO extends ScanObjectDAO {

    private FirebaseFirestore db;

    private CollectionReference expCollection;

    public ScanObjectFSDAO() {
        this(FirebaseFirestore.getInstance());
    }

    public ScanObjectFSDAO(FirebaseFirestore db) {
        this.db = db;
        expCollection = db.collection("experiment");
    }

    /**
     * Gets the collection reference to an experiment's scans
     * @param expId The experiment ID
     * @return Collection Reference
     */
    private CollectionReference getScanSubCollection(String expId) {
        return expCollection.document(expId).collection("scans");
    }

    /**
     * Creates entry of scan object on DB if it doesn't exist.
     * Updates the document if it does exist.
     * scanObj with no keys will automatically have a key generated for them.
     *
     * @param scanObj The can object to add to db.
     * @return Task with scan object.
     */
    @Override
    public Task<String> createUpdateScanObj(ScanObj scanObj) {
        if (scanObj.getKey() == null || scanObj.equals("")) {
            // Create a new document.
            return getScanSubCollection(scanObj.getexpID()).add(scanObj).continueWith(task -> {
                return task.getResult().getId();
            });
        }
        else {
            // Use provided key
            return getScanSubCollection(scanObj.getexpID())
                    .document(scanObj.getKey())
                    .set(scanObj).continueWith(task -> scanObj.getKey());
        }
    }

    /**
     * Gets the scan object based on the key.
     *
     * @param key The key of the scanned object.
     * @param expId Experiment we are interested in.
     * @return Task with Scan object.
     */
    @Override
    public Task<ScanObj> getScanObj(String key, String expId) {
        return getScanSubCollection(expId).document(key).get().continueWith(task -> {
            return task.getResult().toObject(ScanObj.class);
        });
    }
}
