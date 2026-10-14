package com.exfood.deliveryapp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exfood.deliveryapp.Entity.Resturantloginentity;

@Repository
public interface Resturantloginrepo extends JpaRepository<Resturantloginentity, Long> {

    Resturantloginentity findByRestaurantNameAndPassword(String restaurantName, String password);

    Resturantloginentity findByRestaurantName(String restaurantName);

    Resturantloginentity findByEmail(String email);

    boolean existsByRestaurantName(String restaurantName);

    boolean existsByEmail(String email);
}