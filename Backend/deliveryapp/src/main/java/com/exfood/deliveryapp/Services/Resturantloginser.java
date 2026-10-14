package com.exfood.deliveryapp.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exfood.deliveryapp.Entity.Resturantloginentity;
import com.exfood.deliveryapp.Repo.Resturantloginrepo;
import com.exfood.deliveryapp.Security.PasswordUtil;

@Service
public class Resturantloginser {

    @Autowired
    private Resturantloginrepo rlp;

    // CREATE / REGISTER ACCOUNT
    public String insertdata11(Resturantloginentity e) {
        if (e.getRestaurantName() == null || e.getRestaurantName().trim().isEmpty()) {
            return "Restaurant name is required";
        }

        String name = e.getRestaurantName().trim();
        if (rlp.existsByRestaurantName(name)) {
            return "Restaurant already exists";
        }

        if (e.getEmail() != null && !e.getEmail().trim().isEmpty()) {
            String cleanEmail = e.getEmail().trim().toLowerCase();
            if (rlp.existsByEmail(cleanEmail)) {
                return "An account with this email already exists";
            }
            e.setEmail(cleanEmail);
        }

        if (e.getPassword() == null || e.getPassword().trim().length() < 4) {
            return "Password must be at least 4 characters";
        }

        e.setRestaurantName(name);
        e.setPassword(PasswordUtil.hashPassword(e.getPassword().trim()));
        e.setRole("RESTAURANT");
        e.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        rlp.save(e);
        return "Account created successfully";
    }

    // GET ALL RESTAURANTS (Public profile only, no passwords)
    public List<Resturantloginentity> getAllRestaurants() {
        return rlp.findAll().stream().map(r -> {
            Resturantloginentity safe = new Resturantloginentity();
            safe.setRestaurantId(r.getRestaurantId());
            safe.setRestaurantName(r.getRestaurantName());
            safe.setEmail(r.getEmail());
            safe.setPhone(r.getPhone());
            safe.setRole(r.getRole());
            safe.setCreatedAt(r.getCreatedAt());
            return safe;
        }).collect(Collectors.toList());
    }

    // LOGIN
    public Resturantloginentity login(String identifier, String password) {
        if (identifier == null || identifier.trim().isEmpty() || password == null) {
            return null;
        }

        String cleanIdentifier = identifier.trim();
        Resturantloginentity restaurant = rlp.findByRestaurantName(cleanIdentifier);

        // If not found by name, check by email
        if (restaurant == null) {
            restaurant = rlp.findByEmail(cleanIdentifier.toLowerCase());
        }

        if (restaurant != null && PasswordUtil.verifyPassword(password.trim(), restaurant.getPassword())) {
            Resturantloginentity safe = new Resturantloginentity();
            safe.setRestaurantId(restaurant.getRestaurantId());
            safe.setRestaurantName(restaurant.getRestaurantName());
            safe.setEmail(restaurant.getEmail());
            safe.setPhone(restaurant.getPhone());
            safe.setRole("RESTAURANT");
            return safe;
        }

        return null;
    }
}