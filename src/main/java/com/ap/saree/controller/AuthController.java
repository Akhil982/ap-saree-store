package com.ap.saree.controller;

import com.ap.saree.dto.AuthResponse;
import com.ap.saree.dto.OtpRequest;
import com.ap.saree.dto.VerificationRequest;
import com.ap.saree.service.EmailAuthService;
import com.ap.saree.service.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EmailAuthService emailAuthService;
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final Map<String, Boolean> registeredUsers = new ConcurrentHashMap<>();

    public AuthController(EmailAuthService emailAuthService) {
        this.emailAuthService = emailAuthService;
    }

    @PostMapping("/generate-otp")
    public ResponseEntity<Map<String, String>> generateOtp(@RequestBody OtpRequest request) {
        String email = request.email();

        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Please provide a valid email structure address."));
        }

        String activeOtp = String.format("%06d", new Random().nextInt(999999));
        boolean statusSuccess = emailAuthService.sendOtpEmail(email, activeOtp);

        if (!statusSuccess) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "The server's internal email delivery system is busy. Please try again."));
        }

        otpStorage.put(email, activeOtp);
        return ResponseEntity.ok(Map.of("message", "Authentication passcode sent to your email inbox successfully."));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerificationRequest request) {
        String email = request.email();
        String enteredOtp = request.otp();

        if (!otpStorage.containsKey(email) || !otpStorage.get(email).equals(enteredOtp)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired verification pass token sequence."));
        }

        otpStorage.remove(email);

        boolean isNewUser = !registeredUsers.containsKey(email);
        if (isNewUser) {
            registeredUsers.put(email, true);
        }

        String mockJwtToken = "zari_secure_jwt_token_stub_" + Long.toHexString(Double.doubleToLongBits(Math.random()));

        return ResponseEntity.ok(new AuthResponse(
                mockJwtToken,
                isNewUser ? "Registration complete! Welcome to Zari." : "Login validation successful.",
                isNewUser
        ));
    }
}
