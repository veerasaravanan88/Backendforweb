package com.exfood.deliveryapp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exfood.deliveryapp.Entity.Restdishentity;
import com.exfood.deliveryapp.Repo.Restdishrepo;

import java.util.List;
import java.util.Optional;

@Service
public class Restdishser {

    @Autowired
    private Restdishrepo rp;

    public Restdishentity saveDish(Restdishentity dish) {
        return rp.save(dish);
    }

    public List<Restdishentity> getDishesByRestaurantId(Long restaurantId) {
        return rp.findByRestaurantId(restaurantId);
    }

    public void deleteDish(Long id) {
        rp.deleteById(id);
    }

    public Restdishentity updateDish(Restdishentity dish) {
        return rp.save(dish);
    }

    public Optional<Restdishentity> getDishById(Long id) {
        return rp.findById(id);
    }


    public List<Restdishentity> getDishesByRestaurantName(String restaurantName) {
    return rp.findByRestaurantName(restaurantName);
}
}