package com.example.atm.service;

import com.example.atm.model.AuthRequest;
import com.example.atm.model.BalanceResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AtmService {

    // ⚠️ Veri Tabanı simülasyonu (Gerçekte DB kullanılmalı)
    private final Map<String, String> userPins = new HashMap<>();
    private final Map<String, Double> userBalances = new HashMap<>();

    public AtmService() {
        // Test verileri
        userPins.put("1111222233334444", "1234");
        userBalances.put("1111222233334444", 1500.50);
        userPins.put("5555666677778888", "4321");
        userBalances.put("5555666677778888", 500.00);
    }

    public boolean verify(AuthRequest req) {
        String storedPin = userPins.get(req.getCardNumber());
        return storedPin != null && storedPin.equals(req.getPin());
    }

    public BalanceResponse getBalance(String cardNumber) {
        Double balance = userBalances.get(cardNumber);
        if (balance == null) {
            return null; // Kart bulunamadı
        }
        return new BalanceResponse(cardNumber, balance, "Bakiye başarıyla sorgulandı.");
    }

    public BalanceResponse withdraw(String cardNumber, double amount) {
        Double currentBalance = userBalances.get(cardNumber);
        
        if (currentBalance == null) {
            return null; // Kart bulunamadı
        }

        if (currentBalance < amount) {
            // Yetersiz bakiye
            return new BalanceResponse(cardNumber, currentBalance, "Yetersiz bakiye."); 
        }

        // İşlem başarılı
        double newBalance = currentBalance - amount;
        userBalances.put(cardNumber, newBalance);

        return new BalanceResponse(cardNumber, newBalance, "Para çekme işlemi başarıyla gerçekleştirildi.");
    }
}