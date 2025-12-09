package com.example.atm.model;


public class WithdrawRequest {
    private String cardNumber;
    private double amount;


    public String getCardNumber() {
        return cardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}