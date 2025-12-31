package com.example.atm.service;

import com.example.atm.model.AuthRequest;
import com.example.atm.model.BalanceResponse;
import com.example.atm.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class AtmService {

    private final Map<String, String> userPins = new HashMap<>();
    private final Map<String, Double> userBalances = new HashMap<>();
    private final Map<Integer, Integer> atmStock = new TreeMap<>((a, b) -> b - a);

    public AtmService() {
        userPins.put("1111222233334444", "1234");
        userBalances.put("1111222233334444", 5000.00);
        userPins.put("5555666677778888", "4321");
        userBalances.put("5555666677778888", 1000.00);

        atmStock.put(200, 10);
        atmStock.put(100, 10);
        atmStock.put(50, 20);
        atmStock.put(20, 30);
        atmStock.put(10, 50);
        atmStock.put(5, 100);
    }

    public boolean verify(AuthRequest req) {
        String storedPin = userPins.get(req.getCardNumber());
        return storedPin != null && storedPin.equals(req.getPin());
    }

    public BalanceResponse getBalance(String cardNumber) {
        Double balance = userBalances.get(cardNumber);
        if (balance == null) {
            throw new NotFoundException("Kart numarası " + cardNumber + " ile ilişkili hesap bulunamadı.");
        }
        return new BalanceResponse(cardNumber, balance, "Bakiye başarıyla sorgulandı.");
    }

    public BalanceResponse withdraw(String cardNumber, double amount) {
        if (amount <= 0 || amount % 5 != 0) {
            throw new IllegalArgumentException("Lütfen 5 TL ve katları şeklinde bir tutar giriniz.");
        }

        Double currentBalance = userBalances.get(cardNumber);
        if (currentBalance == null) {
            throw new NotFoundException("Hesap bulunamadı.");
        }

        if (currentBalance < amount) {
            throw new IllegalArgumentException("Yetersiz bakiye. Mevcut bakiye: " + currentBalance);
        }

        Map<Integer, Integer> withdrawPlan = calculateBanknotes(amount);
        if (withdrawPlan == null) {
            throw new IllegalArgumentException("ATM'de bu tutarı karşılayacak uygun banknot bulunmamaktadır.");
        }

        updateAtmStock(withdrawPlan);
        double newBalance = currentBalance - amount;
        userBalances.put(cardNumber, newBalance);

        return new BalanceResponse(cardNumber, newBalance, "İşlem başarılı. Verilen banknotlar: " + withdrawPlan.toString());
    }

    public BalanceResponse deposit(String cardNumber, Map<Integer, Integer> depositedBanknotes) {
        Double currentBalance = userBalances.get(cardNumber);
        if (currentBalance == null) {
            throw new NotFoundException("Hesap bulunamadı.");
        }

        double totalDeposited = 0;
        for (Map.Entry<Integer, Integer> entry : depositedBanknotes.entrySet()) {
            int value = entry.getKey();
            int count = entry.getValue();

            if (count < 0) {
                throw new IllegalArgumentException("Negatif banknot adedi girilemez.");
            }

            atmStock.put(value, atmStock.getOrDefault(value, 0) + count);
            totalDeposited += (value * count);
        }

        double newBalance = currentBalance + totalDeposited;
        userBalances.put(cardNumber, newBalance);

        return new BalanceResponse(cardNumber, newBalance, totalDeposited + " TL başarıyla yatırıldı.");
    }

    private Map<Integer, Integer> calculateBanknotes(double amount) {
        Map<Integer, Integer> plan = new LinkedHashMap<>();
        int remaining = (int) amount;

        for (Map.Entry<Integer, Integer> entry : atmStock.entrySet()) {
            int banknotValue = entry.getKey();
            int availableCount = entry.getValue();

            if (remaining >= banknotValue) {
                int neededCount = remaining / banknotValue;
                int actualGiven = Math.min(neededCount, availableCount);

                if (actualGiven > 0) {
                    plan.put(banknotValue, actualGiven);
                    remaining -= (actualGiven * banknotValue);
                }
            }
        }

        return (remaining == 0) ? plan : null;
    }

    private void updateAtmStock(Map<Integer, Integer> plan) {
        for (Map.Entry<Integer, Integer> entry : plan.entrySet()) {
            int value = entry.getKey();
            int count = entry.getValue();
            atmStock.put(value, atmStock.get(value) - count);
        }
    }
}