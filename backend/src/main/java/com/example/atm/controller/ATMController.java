package com.example.atm.controller;

import com.example.atm.model.*;
import com.example.atm.service.AtmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class ATMController {

    private final AtmService atmService;

    public ATMController(AtmService atmService) {
        this.atmService = atmService;
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> balance(@RequestParam String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Kart numarası gereklidir.");
        }
        return ResponseEntity.ok(atmService.getBalance(cardNumber));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<BalanceResponse> withdraw(@RequestBody WithdrawRequest req) {
        if (req.getCardNumber() == null || req.getAmount() <= 0) {
            throw new IllegalArgumentException("Geçersiz kart numarası veya miktar.");
        }
        return ResponseEntity.ok(atmService.withdraw(req.getCardNumber(), req.getAmount()));
    }

    @PostMapping("/deposit")
    public ResponseEntity<BalanceResponse> deposit(@RequestBody DepositRequest req) {
        if (req.getCardNumber() == null || req.getBanknotes() == null || req.getBanknotes().isEmpty()) {
            throw new IllegalArgumentException("Geçersiz kart numarası veya banknot bilgisi.");
        }
        return ResponseEntity.ok(atmService.deposit(req.getCardNumber(), req.getBanknotes()));
    }
}