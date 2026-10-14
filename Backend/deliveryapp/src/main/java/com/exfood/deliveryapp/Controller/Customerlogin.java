package com.exfood.deliveryapp.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exfood.deliveryapp.Entity.Customerlogentity;
import com.exfood.deliveryapp.Services.Customerloginser;

@RestController 
@RequestMapping("/Customerlogin")
@CrossOrigin(origins = "*")
public class Customerlogin {

    @Autowired 
    private Customerloginser cl;

    // CUSTOMER REGISTRATION
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customerlogentity customer) {
        try {
            Customerlogentity saved = cl.register(customer);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Registration successful");
            response.put("customer", saved);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "Registration failed: " + e.getMessage()));
        }
    }

    // CUSTOMER LOGIN (Supports phone or email, authenticates password)
    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody Map<String, Object> credentials) {
        try {
            String identifier = null;
            if (credentials.containsKey("phonenumber")) {
                identifier = String.valueOf(credentials.get("phonenumber"));
            } else if (credentials.containsKey("email")) {
                identifier = (String) credentials.get("email");
            } else if (credentials.containsKey("identifier")) {
                identifier = (String) credentials.get("identifier");
            }

            String password = credentials.containsKey("password") ? String.valueOf(credentials.get("password")) : null;

            if (identifier == null || identifier.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("status", "error", "message", "Phone number/Email and password are required"));
            }

            Customerlogentity authenticated = cl.login(identifier, password);
            if (authenticated != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("message", "Login successful");
                response.put("phonenumber", authenticated.getPhonenumber());
                response.put("name", authenticated.getName());
                response.put("email", authenticated.getEmail());
                response.put("role", authenticated.getRole());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("status", "error", "message", "Invalid mobile number or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "Login error: " + e.getMessage()));
        }
    }
}
