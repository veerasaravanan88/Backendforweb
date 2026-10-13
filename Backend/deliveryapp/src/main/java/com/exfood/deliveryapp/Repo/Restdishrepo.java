package com.exfood.deliveryapp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exfood.deliveryapp.Entity.Restdishentity;

import java.util.List;

@Repository
public interface Restdishrepo extends JpaRepository<Restdishentity, Long> {
    
    // Spring Data JPA automatically translates this to: 
    // SELECT * FROM dish WHERE restaurant_id = ?
    List<Restdishentity> findByRestaurantId(Long restaurantId);



    List<Restdishentity> findByRestaurantName(String restaurantName);
}