package com.example.atm.model;

public class BalanceResponse {
    private String cardNumber;
    private double balance;
    private String message;

    public BalanceResponse(String cardNumber, double balance, String message) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.message = message;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getBalance() {
        return balance;
    }
    
    public String getMessage() {
        return message;
    }
}