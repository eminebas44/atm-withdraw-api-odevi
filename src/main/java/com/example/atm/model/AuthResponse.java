package com.example.atm.model;

// Yanıtı taşıyan model
public class AuthResponse {
    private boolean success;
    private String message;
    private String token; // Güvenlik token'ı (örnek)

    public AuthResponse(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }

    // Getter'lar
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
    
    public String getToken() {
        return token;
    }
}