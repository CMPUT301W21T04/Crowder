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
                if (exp.getName().toLowerCase().contains(search.toLowerCase())){
                    filteredExperiments.add(exp);
                    break;
                }
            }
        }

        return filteredExperiments;
    }

}
