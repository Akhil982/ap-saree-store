package com.ap.saree.dto;

public record VerificationRequest(
        String email,
        String otp
) {}
