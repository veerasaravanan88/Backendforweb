package com.exfood.deliveryapp.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exfood.deliveryapp.Entity.Resturantloginentity;
import com.exfood.deliveryapp.Repo.Resturantloginrepo;

@Service
public class Resturantloginser {

    @Autowired
    Resturantloginrepo rlp;

    // CREATE ACCOUNT
    public String insertdata11(Resturantloginentity e) {

        if (rlp.existsByRestaurantName(e.getRestaurantName())) {
            return "Restaurant already exists";
        }

        rlp.save(e);

        return "Account created successfully";
    }

    // GET ALL
    public List<Resturantloginentity> getAllRestaurants() {

        return rlp.findAll();
    }

    // LOGIN
    public Resturantloginentity login(
            String restaurantName,
            String password) {

        return rlp.findByRestaurantNameAndPassword(
                restaurantName,
                password
        );
    }
}