package com.example.crowderapp.models;

public class UserFilterListItem {
    private String user;
    private boolean isChecked;

    public UserFilterListItem(String user, boolean isChecked) {
        this.user = user;
        this.isChecked = isChecked;
    }

    public String getUser() { return this.user; }

    public boolean getIsChecked() { return this.isChecked; }

    public void setChecked(boolean value) { this.isChecked = value; }

}
