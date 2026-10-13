package com.exfood.deliveryapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.exfood.deliveryapp.Entity.Restdishentity;
import com.exfood.deliveryapp.Services.Restdishser;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/Resturantdish")
@CrossOrigin(origins = "*") 
public class Restdishdata {

    @Autowired
    private Restdishser rds;

    @PostMapping("/insertdish")
    public ResponseEntity<Restdishentity> insertDish(
            @RequestParam("restaurantId") Long restaurantId,
            @RequestParam("restaurantName") String restaurantName,
            @RequestParam("dishName") String dishName,
            @RequestParam("category") String category,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("description") String description,
            @RequestParam(value = "dishImage", required = false) MultipartFile file) throws IOException {

        Restdishentity dish = new Restdishentity();
        dish.setRestaurantId(restaurantId);
        dish.setRestaurantName(restaurantName);
        dish.setDishName(dishName);
        dish.setCategory(category);
        dish.setPrice(price);
        dish.setQuantity(quantity);
        dish.setDescription(description);

     if (file != null && !file.isEmpty()) {
    dish.setDishImage(file.getBytes());
}

        Restdishentity savedDish = rds.saveDish(dish);
        return ResponseEntity.ok(savedDish);
    }

    @GetMapping("/restaurantId/{restaurantId}")
    public ResponseEntity<List<Restdishentity>> getDishesByRestaurantId(@PathVariable Long restaurantId) {
        List<Restdishentity> dishes = rds.getDishesByRestaurantId(restaurantId);
        return ResponseEntity.ok(dishes);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDish(@PathVariable Long id) {
        rds.deleteDish(id);
        return ResponseEntity.ok("Deleted");
    }

    @PutMapping("/update")
    public ResponseEntity<Restdishentity> updateDish(@RequestBody Restdishentity dish) {
        return ResponseEntity.ok(rds.updateDish(dish));
    }


    @GetMapping("/restaurant/{restaurantName}")
public ResponseEntity<List<Restdishentity>> getDishesByRestaurantName(
        @PathVariable String restaurantName) {

    List<Restdishentity> dishes =
            rds.getDishesByRestaurantName(restaurantName);

    return ResponseEntity.ok(dishes);
}
}