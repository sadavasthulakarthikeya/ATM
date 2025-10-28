package com.example.atm.project;

public class LoginRequest {

    private String accountNumber;
    private String pin;

    // Constructors
    public LoginRequest() {}

    public LoginRequest(String accountNumber, String pin) {
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
