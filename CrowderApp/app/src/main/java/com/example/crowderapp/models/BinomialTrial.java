package com.example.crowderapp.models;

import java.util.Date;

public class BinomialTrial extends Trial {
    private boolean result;

    public BinomialTrial(String experimenter, Date date, boolean result) {
        super(experimenter, date);
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
