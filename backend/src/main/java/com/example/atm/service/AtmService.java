package com.example.atm.service;

import com.example.atm.model.AuthRequest;
import com.example.atm.model.BalanceResponse;
import com.example.atm.model.User;
import com.example.atm.repository.UserRepository;
import com.example.atm.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtmService {

    private final UserRepository userRepository;

    public AtmService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean verify(AuthRequest req) {
        Optional<User> userOptional = userRepository.findByUsername(req.getCardNumber());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getPassword().equals(req.getPin());
        }
        return false;
    }

    public BalanceResponse getBalance(String cardNumber) {
        User user = userRepository.findByUsername(cardNumber)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı: " + cardNumber));
        
        return new BalanceResponse(cardNumber, user.getBalance(), "Bakiye başarıyla sorgulandı.");
    }

    public BalanceResponse withdraw(String cardNumber, double amount) {

        User user = userRepository.findByUsername(cardNumber)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı: " + cardNumber));

        if (user.getBalance() < amount) {
            throw new IllegalArgumentException("Yetersiz bakiye. Mevcut bakiye: " + user.getBalance());
        }

        double newBalance = user.getBalance() - amount;
        user.setBalance(newBalance);
    
        userRepository.save(user);

        return new BalanceResponse(cardNumber, newBalance, "Para çekme işlemi başarıyla gerçekleştirildi.");
    }
}