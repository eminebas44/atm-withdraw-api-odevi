package com.example.atm.model;

// İsteği taşıyan model
public class AuthRequest {
    private String cardNumber;
    private String pin;

    // Getter'lar
    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    // Setter'lar
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}