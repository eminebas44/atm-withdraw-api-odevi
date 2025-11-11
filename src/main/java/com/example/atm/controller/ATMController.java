package com.example.atm.controller;

import com.example.atm.model.*;
import com.example.atm.service.AtmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class ATMController {

    private final AtmService atmService;

    // Bağımlılık Enjeksiyonu (Constructor Injection)
    public ATMController(AtmService atmService) {
        this.atmService = atmService;
    }

    /**
     * POST /account/verify - Kimlik Doğrulama
     */
    @PostMapping("/verify")
    public ResponseEntity<AuthResponse> verify(@RequestBody AuthRequest req) {
        
        // Basit boş kontrolü
        if (req.getCardNumber() == null || req.getPin() == null) {
            return ResponseEntity.badRequest().body(new AuthResponse(false, "Eksik parametre: Kart No veya PIN gerekli.", null));
        }

        boolean ok = atmService.verify(req);

        if (ok) {
            // Başarılı kimlik doğrulama, token yerine kart numarasını döndürebiliriz (örnek)
            return ResponseEntity.ok(new AuthResponse(true, "Doğrulama başarılı.", req.getCardNumber()));
        } else {
            // Başarısız kimlik doğrulama
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(false, "Kart veya PIN hatalı.", null));
        }
    }

    /**
     * GET /account/balance?cardNumber={cardNumber} - Bakiye Sorgulama
     */
    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> balance(@RequestParam String cardNumber) {

        // Kart numarası kontrolü
        if (cardNumber == null || cardNumber.isEmpty()) {
            return ResponseEntity.badRequest().body(new BalanceResponse(null, 0.0, "Eksik parametre: Kart No gerekli."));
        }
        
        BalanceResponse response = atmService.getBalance(cardNumber);

        if (response != null && !response.getMessage().equals("Bakiye başarıyla sorgulandı.")) {
             // Bu senaryoda null dönmeyecek, ancak bir hata mesajı alırsak kontrol edebiliriz
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BalanceResponse(cardNumber, 0.0, "Kart bulunamadı."));
        }
        
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BalanceResponse(cardNumber, 0.0, "Kart bulunamadı."));
        }

        return ResponseEntity.ok(response);
    }

    /**
     * POST /account/withdraw - Para Çekme
     */
    @PostMapping("/withdraw")
    public ResponseEntity<BalanceResponse> withdraw(@RequestBody WithdrawRequest req) {

        // Basit veri kontrolü
        if (req.getCardNumber() == null || req.getAmount() <= 0) {
            return ResponseEntity.badRequest().body(new BalanceResponse(null, 0.0, "Eksik veya geçersiz miktar/kart numarası."));
        }

        BalanceResponse response = atmService.withdraw(req.getCardNumber(), req.getAmount());

        if (response == null) {
            // Kart bulunamadı
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BalanceResponse(req.getCardNumber(), 0.0, "Kart bulunamadı."));
        }
        
        if (response.getMessage().equals("Yetersiz bakiye.")) {
             // Yetersiz bakiye durumu
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        return ResponseEntity.ok(response);
    }
}