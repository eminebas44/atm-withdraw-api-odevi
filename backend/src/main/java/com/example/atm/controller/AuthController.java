package com.example.atm.controller;

import com.example.atm.model.AuthRequest;
//import com.example.atm.model.AuthResponse;
import com.example.atm.service.AtmService;
import com.example.atm.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AtmService atmService;
    private final JwtService jwtService;

    public AuthController(AtmService atmService, JwtService jwtService) {
        this.atmService = atmService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
       
        String cardNumber = request.get("username");
        String pin = request.get("password");

        AuthRequest authRequest = new AuthRequest();
        authRequest.setCardNumber(cardNumber);
        authRequest.setPin(pin);

        boolean isAuthenticated = atmService.verify(authRequest);

        if (isAuthenticated) {
           
            String token = jwtService.generateToken(cardNumber);
            
         
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Giriş Başarılı");
            
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Kart numarası veya PIN hatalı"));
        }
    }
}