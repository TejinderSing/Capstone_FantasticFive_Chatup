package com.example.chatupgmail.Models;

import java.util.ArrayList;

public class UserStatus {
    private String name;
    private long lastUpdated;
    private ArrayList<Status> statuses;

    public UserStatus(String name,  long lastUpdated, ArrayList<Status> statuses) {
        this.name = name;
        this.lastUpdated = lastUpdated;
        this.statuses = statuses;
    }

    public UserStatus() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }
}
