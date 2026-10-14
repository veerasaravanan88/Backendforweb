package com.exfood.deliveryapp.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exfood.deliveryapp.Entity.Customerlogentity;
import com.exfood.deliveryapp.Repo.Customerloginrepo;
import com.exfood.deliveryapp.Security.PasswordUtil;

@Service 
public class Customerloginser {

    @Autowired 
    private Customerloginrepo clp;

    /**
     * Legacy support: inserts or updates data.
     */
    public String insertdata(Customerlogentity e) {
        if (e.getPassword() != null && !e.getPassword().trim().isEmpty()) {
            e.setPassword(PasswordUtil.hashPassword(e.getPassword().trim()));
        }
        if (e.getCreatedAt() == null) {
            e.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        clp.save(e);
        return "registered";
    }

    /**
     * Register customer with unique phone and email check.
     */
    public Customerlogentity register(Customerlogentity customer) {
        if (customer.getPhonenumber() <= 0) {
            throw new IllegalArgumentException("Valid 10-digit phone number is required");
        }
        if (clp.existsByPhonenumber(customer.getPhonenumber())) {
            throw new IllegalStateException("An account with this phone number already exists");
        }
        if (customer.getEmail() != null && !customer.getEmail().trim().isEmpty()) {
            String cleanEmail = customer.getEmail().trim().toLowerCase();
            if (clp.existsByEmail(cleanEmail)) {
                throw new IllegalStateException("An account with this email address already exists");
            }
            customer.setEmail(cleanEmail);
        }

        if (customer.getPassword() == null || customer.getPassword().trim().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }

        customer.setPassword(PasswordUtil.hashPassword(customer.getPassword().trim()));
        customer.setRole("CUSTOMER");
        customer.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Customerlogentity saved = clp.save(customer);
        saved.setPassword(null); // Never return password
        return saved;
    }

    /**
     * Authenticate customer by phone or email.
     */
    public Customerlogentity login(String identifier, String password) {
        if (identifier == null || identifier.trim().isEmpty() || password == null) {
            return null;
        }

        String trimmed = identifier.trim();
        Optional<Customerlogentity> userOpt = Optional.empty();

        // Check if numeric phone number
        if (trimmed.matches("^\\d+$")) {
            try {
                long phone = Long.parseLong(trimmed);
                userOpt = clp.findByPhonenumber(phone);
            } catch (NumberFormatException ignored) {}
        }

        // If not found by phone, search by email
        if (userOpt.isEmpty()) {
            userOpt = clp.findByEmail(trimmed.toLowerCase());
        }

        if (userOpt.isPresent()) {
            Customerlogentity user = userOpt.get();
            if (PasswordUtil.verifyPassword(password.trim(), user.getPassword())) {
                Customerlogentity safe = new Customerlogentity();
                safe.setPhonenumber(user.getPhonenumber());
                safe.setName(user.getName() != null ? user.getName() : "Customer");
                safe.setEmail(user.getEmail());
                safe.setRole(user.getRole() != null ? user.getRole() : "CUSTOMER");
                safe.setCreatedAt(user.getCreatedAt());
                return safe;
            }
        }
        return null;
    }
}
