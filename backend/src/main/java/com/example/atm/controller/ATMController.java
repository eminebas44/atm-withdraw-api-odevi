package com.example.atm.controller;

import com.example.atm.model.*;
import com.example.atm.service.AtmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// SWAGGER İÇİN GEREKLİ OLAN IMPORT:
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/account")
// BU ANNOTATION SWAGGER'A BURANIN KİLİTLİ OLDUĞUNU SÖYLER:
@SecurityRequirement(name = "bearerAuth") 
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

        BalanceResponse response = atmService.getBalance(cardNumber);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<BalanceResponse> withdraw(@RequestBody WithdrawRequest req) {
        if (req.getCardNumber() == null || req.getAmount() <= 0) {
            throw new IllegalArgumentException("Geçersiz kart numarası veya miktar.");
        }

        BalanceResponse response = atmService.withdraw(req.getCardNumber(), req.getAmount());
        return ResponseEntity.ok(response);
    }
}