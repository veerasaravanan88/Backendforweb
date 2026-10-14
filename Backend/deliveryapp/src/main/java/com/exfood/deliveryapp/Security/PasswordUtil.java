package com.exfood.deliveryapp.Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Secure password hashing utility using salted SHA-256 with constant-time comparison.
 * Backward-compatible with existing plaintext passwords.
 */
public class PasswordUtil {

    private static final String PREFIX = "$SHA256$";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Hashes a plaintext password with a random 16-byte salt.
     */
    public static String hashPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hash = computeHash(password, salt);
        return PREFIX + saltBase64 + "$" + hash;
    }

    /**
     * Verifies a plaintext password against a stored hash (or legacy plaintext).
     */
    public static boolean verifyPassword(String rawPassword, String storedValue) {
        if (rawPassword == null || storedValue == null) {
            return false;
        }

        // Check if storedValue uses our salted hash format
        if (storedValue.startsWith(PREFIX)) {
            try {
                String payload = storedValue.substring(PREFIX.length());
                String[] parts = payload.split("\\$");
                if (parts.length != 2) {
                    return false;
                }
                byte[] salt = Base64.getDecoder().decode(parts[0]);
                String expectedHash = parts[1];
                String actualHash = computeHash(rawPassword, salt);
                return MessageDigest.isEqual(
                        expectedHash.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                        actualHash.getBytes(java.nio.charset.StandardCharsets.UTF_8)
                );
            } catch (Exception ex) {
                return false;
            }
        }

        // Backward compatibility: match legacy plaintext password
        return MessageDigest.isEqual(
                rawPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                storedValue.getBytes(java.nio.charset.StandardCharsets.UTF_8)
        );
    }

    private static String computeHash(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashed = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
