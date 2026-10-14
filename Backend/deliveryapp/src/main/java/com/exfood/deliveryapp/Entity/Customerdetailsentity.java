package com.exfood.deliveryapp.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "customer_orders")
public class Customerdetailsentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phonenumber")
    private long phonenumber; 

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city; 

    @Column(name = "pincode")
    private String pincode; 

    @Column(name = "payment")
    private String payment;

    @Column(name = "status")
    private String status = "Pending";

    @Column(name = "amount")
    private double amount;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "items_summary", length = 1000)
    private String itemsSummary;

    @Column(name = "created_at")
    private String createdAt;
}
