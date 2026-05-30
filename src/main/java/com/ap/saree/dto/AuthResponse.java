package com.ap.saree.dto;

public record AuthResponse(
        String token,
        String message,
        boolean isNewUser
) {}
