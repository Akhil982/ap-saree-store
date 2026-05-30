package com.ap.saree.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class SmsService {

    @Value("${sms.gateway.api-key}")
    private String apiKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Dispatches real-time security codes over Indian carrier channels.
     */
    public boolean sendOtpMessage(String mobileNumber, String otpCode) {
        try {
            // Step A: Formulate clean, readable text copy
            String messageContent = "Your ZARI login security authentication code is: " + otpCode + ". Active for 5 minutes.";

            // Step B: URL encode the message string text safely
            String encodedMessage = java.net.URLEncoder.encode(messageContent, java.nio.charset.StandardCharsets.UTF_8);

            // Step C: UPDATED - Switched route parameters to "q" (Quick SMS Route)
            // This structural layout drops the website verification requirements entirely
            String targetUrl = "https://www.fast2sms.com/dev/bulkV2"
                    + "?authorization=" + apiKey
                    + "&route=q"
                    + "&message=" + encodedMessage
                    + "&numbers=" + mobileNumber
                    + "&flash=0";

            // Step D: Build the system-level network request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create(targetUrl))
                    .GET()
                    .header("accept", "application/json")
                    .build();

            // Step E: Execute call synchronously
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("--> [SUCCESS] Quick SMS routed successfully to handset: " + mobileNumber);
                return true;
            } else {
                System.err.println("--> [FAILURE] Quick SMS API error status code: " + response.statusCode());
                System.err.println("--> Diagnostic logs output details: " + response.body());
                return false;
            }

        } catch (Exception e) {
            System.err.println("Critical exception fired during live SMS transmission pipeline: " + e.getMessage());
            return false;
        }
    }
}
