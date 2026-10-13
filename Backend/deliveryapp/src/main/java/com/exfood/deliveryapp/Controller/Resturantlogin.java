package com.exfood.deliveryapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    Resturantloginser rl;


    // =========================
    // CREATE ACCOUNT
    // =========================

    @PostMapping("/register")
    public String register(@RequestBody Resturantloginentity e) {

        return rl.insertdata11(e);

    }


    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")
    public Resturantloginentity login(
            @RequestBody Resturantloginentity e) {

        Resturantloginentity result =
                rl.login(
                        e.getRestaurantName(),
                        e.getPassword()
                );

        return result;
    }


    // =========================
    // GET ALL
    // =========================

    @GetMapping("/all")
    public List<Resturantloginentity> getAllRestaurants() {

        return rl.getAllRestaurants();

    }
}