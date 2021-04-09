package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to search for experiments
 */
public class Search {

    public Search() {
    }

    /**
     * Extra search words to add to search string
     * @return Whether the experiment should be returned in search.
     */
    public String extraProcessing(Experiment exp) {
        return "";
    }

    /**
     * search experiments and return a list matching key words. Synchronous, no users.
     * @param filterStrings keywords to search for
     * @param experimentList list of all experiments
     * @return a list of all experiments matching the keywords
     */
    public List<Experiment> searchExperiments(ArrayList<String> filterStrings, List<Experiment> experimentList) {

        List<Experiment> filteredExperiments = new ArrayList<>();

        for (Experiment exp : experimentList) {
            for (String search : filterStrings) {
                String searchString = (exp.getName() + exp.getExperimentType() + exp.getRegion()).toLowerCase();
                searchString += extraProcessing(exp);
                if (searchString.contains(search.toLowerCase())){
                    filteredExperiments.add(exp);
                    break;
                }
            }
        }

        return filteredExperiments;
    }
}
