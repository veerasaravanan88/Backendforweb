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

    // GET ALL DISHES (Fixes 404 error from Dashboard.js)
    @GetMapping("/getall")
    public ResponseEntity<List<Restdishentity>> getAllDishes() {
        List<Restdishentity> dishes = rds.getAllDishes();
        return ResponseEntity.ok(dishes);
    }

    @PostMapping("/insertdish")
    public ResponseEntity<?> insertDish(
            @RequestParam("restaurantId") Long restaurantId,
            @RequestParam("restaurantName") String restaurantName,
            @RequestParam("dishName") String dishName,
            @RequestParam("category") String category,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "dishImage", required = false) MultipartFile file) {

        try {
            if (dishName == null || dishName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Dish name cannot be empty");
            }
            if (price == null || price <= 0) {
                return ResponseEntity.badRequest().body("Price must be greater than zero");
            }

            Restdishentity dish = new Restdishentity();
            dish.setRestaurantId(restaurantId);
            dish.setRestaurantName(restaurantName != null ? restaurantName.trim() : "");
            dish.setDishName(dishName.trim());
            dish.setCategory(category != null ? category.trim() : "Main Course");
            dish.setPrice(price);
            dish.setQuantity(quantity != null ? Math.max(0, quantity) : 0);
            dish.setDescription(description != null ? description.trim() : "");

            if (file != null && !file.isEmpty()) {
                dish.setDishImage(file.getBytes());
            }

            Restdishentity savedDish = rds.saveDish(dish);
            return ResponseEntity.ok(savedDish);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to process image");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error saving dish: " + e.getMessage());
        }
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
    public ResponseEntity<?> updateDish(@RequestBody Restdishentity dish) {
        if (dish.getId() == null) {
            return ResponseEntity.badRequest().body("Dish ID required for update");
        }
        return ResponseEntity.ok(rds.updateDish(dish));
    }

    @GetMapping("/restaurant/{restaurantName}")
    public ResponseEntity<List<Restdishentity>> getDishesByRestaurantName(@PathVariable String restaurantName) {
        List<Restdishentity> dishes = rds.getDishesByRestaurantName(restaurantName);
        return ResponseEntity.ok(dishes);
    }
}