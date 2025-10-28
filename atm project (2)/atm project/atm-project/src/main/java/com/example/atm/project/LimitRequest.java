package com.example.atm.project;

public class LimitRequest {

    private double dailyLimit;

    // Constructors
    public LimitRequest() {}

    public LimitRequest(double dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    // Getters and Setters
    public double getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(double dailyLimit) {
        this.dailyLimit = dailyLimit;
    }
}
