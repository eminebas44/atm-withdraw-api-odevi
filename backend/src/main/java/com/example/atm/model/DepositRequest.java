package com.example.atm.model;

import java.util.Map;

public class DepositRequest {
    private String cardNumber;
    private Map<Integer, Integer> banknotes;

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public Map<Integer, Integer> getBanknotes() { return banknotes; }
    public void setBanknotes(Map<Integer, Integer> banknotes) { this.banknotes = banknotes; }
}