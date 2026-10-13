package com.exfood.deliveryapp.Repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exfood.deliveryapp.Entity.Customerlogentity;
@Repository 
public interface Customerloginrepo extends JpaRepository<Customerlogentity,Long> {
    
}
