package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.List;

public class Search {


    public Search() {
    }

    public List<Experiment> searchExperiments(ArrayList<String> filterStrings, List<Experiment> experimentList) {

        List<Experiment> filteredExperiments = new ArrayList<>();

        for (Experiment exp : experimentList) {
            for (String search : filterStrings) {
                // TODO: Need username searchable
                String searchString = (exp.getName() + exp.getExperimentType()).toLowerCase();
                if (searchString.contains(search.toLowerCase())){
                    filteredExperiments.add(exp);
                    break;
                }
            }
        }

        return filteredExperiments;
    }

}
