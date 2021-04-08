package com.example.crowderapp.models;

public class Barcode {

    private String action;
    private String barcodeValue;
    private String barcodeId;

    public Barcode() {}


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBarcodeValue() {
        return barcodeValue;
    }

    public void setBarcodeValue(String barcodeValue) {
        this.barcodeValue = barcodeValue;
    }

    public String getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(String barcodeId) {
        this.barcodeId = barcodeId;
    }
}
