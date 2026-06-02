package com.ap.saree.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailAuthService {

    private final JavaMailSender mailSender;

    public EmailAuthService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Dispatches free email OTP verification notifications to client inbox destinations.
     */
    public boolean sendOtpEmail(String targetEmail, String otpCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(targetEmail);
            message.setSubject("⚜️ JAABILI SAREE STORE - Your Secure Access Code");

            String layoutBody = """
                Welcome to Hosue Of Jaabili.
                
                Your luxury portal authentication verification passcode is: %s
                
                This credential sequence remains active for 5 minutes. If you did not request this code, please disregard this email.
                
                Regards,
                The Jaabili Store Dev Engine Team
                """.formatted(otpCode);

            message.setText(layoutBody);

            // Execute free transmission transfer
            mailSender.send(message);
            System.out.println("--> [EMAIL SUCCESS] Security token dispatched cleanly to: " + targetEmail);
            return true;

        } catch (Exception e) {
            System.err.println("Critical error executing SMTP engine transmission call: " + e.getMessage());
            return false;
        }
    }
}
