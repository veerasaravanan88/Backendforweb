
package com.exfood.deliveryapp.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dish")
public class Restdishentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Restaurant ID
    @Column(name = "restaurant_id")
    private Long restaurantId;

    // Restaurant Name
    @Column(name = "restaurant_name")
    private String restaurantName;

    // Dish Name
    @Column(name = "dish_name")
    private String dishName;

    // Dish Category
    private String category;

    // Dish Price
    private Double price;

    // Dish Quantity
    private Integer quantity;

    // Dish Description
    private String description;

    // Dish Image stored as PostgreSQL BYTEA
    @Column(name = "dish_image", columnDefinition = "bytea")
    private byte[] dishImage;


    // ================= DEFAULT CONSTRUCTOR =================

    public Restdishentity() {
    }


    // ================= GETTERS AND SETTERS =================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }


    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // ================= DISH IMAGE =================

    public byte[] getDishImage() {
        return dishImage;
    }

    public void setDishImage(byte[] dishImage) {
        this.dishImage = dishImage;
    }

}
