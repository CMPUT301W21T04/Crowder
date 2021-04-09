package com.example.crowderapp.models;

public class UserFilterListItem {
    private User user;
    private boolean isChecked;

    public UserFilterListItem(User user, boolean isChecked) {
        this.user = user;
        this.isChecked = isChecked;
    }

    public User getUser() { return this.user; }

    public boolean getIsChecked() { return this.isChecked; }

    public void setChecked(boolean value) { this.isChecked = value; }

}
