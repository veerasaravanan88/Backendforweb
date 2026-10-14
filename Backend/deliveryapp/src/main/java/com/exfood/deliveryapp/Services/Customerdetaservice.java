package com.exfood.deliveryapp.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exfood.deliveryapp.Entity.Customerdetailsentity;
import com.exfood.deliveryapp.Repo.Customerdetarepo;

@Service 
public class Customerdetaservice {

    @Autowired 
    private Customerdetarepo crd;

    public Customerdetailsentity insert(Customerdetailsentity e) {
        if (e.getStatus() == null || e.getStatus().trim().isEmpty()) {
            e.setStatus("Pending");
        }
        if (e.getCreatedAt() == null) {
            e.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return crd.save(e);
    }

    public List<Customerdetailsentity> getAllOrders() {
        return crd.findAllByOrderByIdDesc();
    }

    public List<Customerdetailsentity> getOrdersByRestaurant(String restaurantName) {
        if (restaurantName == null || restaurantName.trim().isEmpty()) {
            return getAllOrders();
        }
        return crd.findByRestaurantNameOrderByIdDesc(restaurantName.trim());
    }

    public List<Customerdetailsentity> getOrdersByPhone(long phonenumber) {
        return crd.findByPhonenumberOrderByIdDesc(phonenumber);
    }

    public Optional<Customerdetailsentity> getOrderById(Long id) {
        return crd.findById(id);
    }

    public boolean updateOrderStatus(long id, String newStatus) {
        Optional<Customerdetailsentity> optionalOrder = crd.findById(id);
        if (optionalOrder.isPresent()) {
            Customerdetailsentity order = optionalOrder.get();
            order.setStatus(newStatus);
            crd.save(order);
            return true;
        }

        // Fallback: If id matches a phone number with recent order
        List<Customerdetailsentity> byPhone = crd.findByPhonenumberOrderByIdDesc(id);
        if (!byPhone.isEmpty()) {
            Customerdetailsentity recent = byPhone.get(0);
            recent.setStatus(newStatus);
            crd.save(recent);
            return true;
        }

        return false;
    }
}
