package com.exfood.deliveryapp.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exfood.deliveryapp.Entity.Customerdetailsentity;
import com.exfood.deliveryapp.Services.Customerdetaservice;

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
    Customerdetaservice cds;
    @PostMapping("/inserted")
    public String postMethodName(@RequestBody Customerdetailsentity e) {
        
        cds.insert(e);
        return "inserted";
    }

    @GetMapping("/allorders")
public List<Customerdetailsentity> getAllOrders() {

    return cds.getAllOrders();

}


@PutMapping("/status/{phonenumber}")
    public ResponseEntity<String> updateStatus(@PathVariable long phonenumber, @RequestBody Map<String, String> payload) {
        String newStatus = payload.get("status");
        boolean isUpdated = cds.updateOrderStatus(phonenumber, newStatus);
        
        if (isUpdated) {
            return ResponseEntity.ok("Status updated to " + newStatus);
        } else {
            return ResponseEntity.badRequest().body("Order not found");
        }
    }
    
    
}
