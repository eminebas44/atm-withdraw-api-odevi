package com.example.atm.model;

// Para çekme isteği
public class WithdrawRequest {
    private String cardNumber;
    private double amount;

    // Getter'lar
    public String getCardNumber() {
        return cardNumber;
    }

    public double getAmount() {
        return amount;
    }

    // Setter'lar
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}