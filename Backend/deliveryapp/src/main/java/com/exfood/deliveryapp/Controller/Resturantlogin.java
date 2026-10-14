package com.exfood.deliveryapp.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exfood.deliveryapp.Entity.Resturantloginentity;
import com.exfood.deliveryapp.Services.Resturantloginser;

@RestController
@RequestMapping("/Resturantlogin")
@CrossOrigin(origins = "*")
public class Resturantlogin {

    @Autowired
    private Resturantloginser rl;

    // CREATE / REGISTER RESTAURANT ACCOUNT
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Resturantloginentity e) {
        if (e.getRestaurantName() == null || e.getRestaurantName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Restaurant name is required"));
        }
        if (e.getPassword() == null || e.getPassword().trim().length() < 4) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Password must be at least 4 characters"));
        }

        String result = rl.insertdata11(e);
        if ("Account created successfully".equals(result)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("status", "success", "message", result));
        } else if ("Restaurant already exists".equals(result) || result.contains("already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("status", "error", "message", result));
        } else {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", result));
        }
    }

    // LOGIN RESTAURANT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Resturantloginentity e) {
        if (e.getRestaurantName() == null || e.getPassword() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", "Restaurant name and password are required"));
        }

        Resturantloginentity result = rl.login(e.getRestaurantName(), e.getPassword());
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "error", "message", "Invalid restaurant name or password"));
        }
    }

    // GET ALL RESTAURANTS (Without passwords)
    @GetMapping("/all")
    public List<Resturantloginentity> getAllRestaurants() {
        return rl.getAllRestaurants();
    }
}