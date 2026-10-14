package com.exfood.deliveryapp.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Data 
@Table(name = "Customerlogindetails")
public class Customerlogentity {

    @Id 
    @Column(name = "phonenumber")
    private long phonenumber;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role = "CUSTOMER";

    @Column(name = "created_at")
    private String createdAt;
}
