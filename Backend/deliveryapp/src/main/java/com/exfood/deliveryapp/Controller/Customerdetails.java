package com.exfood.deliveryapp.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exfood.deliveryapp.Entity.Customerdetailsentity;
import com.exfood.deliveryapp.Services.Customerdetaservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
@RequestMapping("/Customerdetails")
@CrossOrigin(origins = "*")
public class Customerdetails {

    @Autowired 
    private Customerdetaservice cds;

    // PLACE / INSERT NEW ORDER
    @PostMapping("/inserted")
    public ResponseEntity<?> postMethodName(@RequestBody Customerdetailsentity order) {
        if (order.getPhonenumber() <= 0) {
            return ResponseEntity.badRequest().body("Valid phone number is required");
        }
        if (order.getName() == null || order.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Customer name is required");
        }
        if (order.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Order amount must be greater than zero");
        }

        Customerdetailsentity saved = cds.insert(order);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "inserted");
        response.put("orderId", saved.getId());
        response.put("phonenumber", saved.getPhonenumber());
        response.put("amount", saved.getAmount());
        return ResponseEntity.ok(response);
    }

    // GET ALL ORDERS (Optionally filtered by restaurant)
    @GetMapping("/allorders")
    public List<Customerdetailsentity> getAllOrders(
            @RequestParam(value = "restaurantName", required = false) String restaurantName) {
        if (restaurantName != null && !restaurantName.trim().isEmpty()) {
            return cds.getOrdersByRestaurant(restaurantName.trim());
        }
        return cds.getAllOrders();
    }

    // GET ORDERS SPECIFICALLY FOR A RESTAURANT (Role & data isolation)
    @GetMapping("/restaurant/{restaurantName}")
    public List<Customerdetailsentity> getRestaurantOrders(@PathVariable String restaurantName) {
        return cds.getOrdersByRestaurant(restaurantName);
    }

    // GET ORDERS FOR A SPECIFIC CUSTOMER PHONE (Customer tracking)
    @GetMapping("/phone/{phonenumber}")
    public List<Customerdetailsentity> getCustomerOrders(@PathVariable long phonenumber) {
        return cds.getOrdersByPhone(phonenumber);
    }

    // UPDATE STATUS BY ORDER ID OR PHONE NUMBER
    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateStatus(
            @PathVariable long id,
            @RequestBody Map<String, String> payload) {

        String newStatus = payload != null ? payload.get("status") : null;
        if (newStatus == null || newStatus.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Status is required");
        }

        boolean isUpdated = cds.updateOrderStatus(id, newStatus.trim());
        if (isUpdated) {
            return ResponseEntity.ok("Status updated to " + newStatus);
        } else {
            return ResponseEntity.badRequest().body("Order not found");
        }
    }
}
