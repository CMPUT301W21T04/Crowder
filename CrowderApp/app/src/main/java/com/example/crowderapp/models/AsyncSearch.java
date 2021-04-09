package com.example.crowderapp.models;

import com.example.crowderapp.models.dao.UserDAO;
import com.example.crowderapp.models.dao.UserFSDAO;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Search with capability to use async info. i.e. Username
 */
public class AsyncSearch {

    Search search;

    private UserDAO userDAO;
    Task<Map<String, String>> userIdToUsernameMap;

    public AsyncSearch() {

        userDAO = new UserFSDAO();

        search = new Search() {
            @Override
            public String extraProcessing(Experiment exp) {
                return AsyncSearch.this.extraProcessing(exp);
            }
        };

        // User Id to user name LUT
        userIdToUsernameMap = userDAO.getAllUsers().continueWith(task -> {
            Map map = new HashMap();

            for (User user : task.getResult()) {
                map.put(user.getUid(), user.getName());
            }

            return map;
        });
    }

    /**
     * Extra search words to add to search string
     * @return Whether the experiment should be returned in search.
     */
    private String extraProcessing(Experiment exp) {
        if(!userIdToUsernameMap.isSuccessful()) {
            throw new RuntimeException("Async Usernames not yet available!");
        }

        Map<String, String> map = userIdToUsernameMap.getResult();
        return map.get(exp.getOwnerID()).toLowerCase();
    }

    /**
     * Filters experiment by search terms.
     * @param filterStrings Search terms
     * @param experimentList The list of experiments to filter.
     * @return Filtered experiments
     */
    public Task<List<Experiment>> searchExperiments(ArrayList<String> filterStrings, List<Experiment> experimentList) {
        return userIdToUsernameMap.continueWith(task -> {
            return search.searchExperiments(filterStrings, experimentList);
        });
    }
}
