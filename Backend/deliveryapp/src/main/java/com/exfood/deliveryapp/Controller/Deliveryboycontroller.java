package com.exfood.deliveryapp.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exfood.deliveryapp.Entity.Deliveryboyentity;
import com.exfood.deliveryapp.Services.Deliveryboyser;

@RestController
@RequestMapping("/Deliveryboy")
@CrossOrigin(origins = "*")
public class Deliveryboycontroller {

    @Autowired
    private Deliveryboyser deliveryService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Deliveryboyentity boy) {
        try {
            Deliveryboyentity created = deliveryService.register(boy);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "success", "data", created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String identifier = credentials.get("identifier");
        if (identifier == null && credentials.containsKey("email")) identifier = credentials.get("email");
        if (identifier == null && credentials.containsKey("phone")) identifier = credentials.get("phone");

        String password = credentials.get("password");

        Deliveryboyentity authenticated = deliveryService.login(identifier, password);
        if (authenticated != null) {
            return ResponseEntity.ok(Map.of("status", "success", "data", authenticated));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "error", "message", "Invalid delivery credentials"));
        }
    }

    @GetMapping("/available")
    public List<Deliveryboyentity> getAvailable() {
        return deliveryService.getAllAvailable();
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String status = payload != null ? payload.get("status") : "Available";
        boolean updated = deliveryService.updateStatus(id, status);
        if (updated) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "Status updated"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Delivery boy not found"));
        }
    }
}
