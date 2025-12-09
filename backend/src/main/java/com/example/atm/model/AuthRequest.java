package com.example.atm.model;


public class AuthRequest {
    private String cardNumber;
    private String pin;


    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

  
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}