package com.example.atm.project;

public class AmountRequest {

    private double amount;

    // Constructors
    public AmountRequest() {}

    public AmountRequest(double amount) {
        this.amount = amount;
    }

    // Getters and Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
