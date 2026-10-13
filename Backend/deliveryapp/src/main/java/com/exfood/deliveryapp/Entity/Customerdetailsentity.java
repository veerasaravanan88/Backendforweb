package com.exfood.deliveryapp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
 @Data
  @Table(name = "checkoutdetails")
public class Customerdetailsentity {
    @Id
     private long phonenumber; 
     private String name;
      private String street;
       private String city; 
       private String pincode; 
       private String payment;
       private String status;
       private double amount;
       
}
