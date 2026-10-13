package com.exfood.deliveryapp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity 
@Data 
@Table(name="Customerlogindetails")
public class Customerlogentity {
    @Id 
    private long phonenumber;
    private String password;

}
