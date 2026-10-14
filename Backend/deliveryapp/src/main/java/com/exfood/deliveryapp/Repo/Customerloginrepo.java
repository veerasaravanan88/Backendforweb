package com.exfood.deliveryapp.Repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exfood.deliveryapp.Entity.Customerlogentity;

@Repository 
public interface Customerloginrepo extends JpaRepository<Customerlogentity, Long> {
    
    Optional<Customerlogentity> findByPhonenumber(long phonenumber);

    Optional<Customerlogentity> findByEmail(String email);

    boolean existsByPhonenumber(long phonenumber);

    boolean existsByEmail(String email);
}
