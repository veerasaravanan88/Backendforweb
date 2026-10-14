package com.exfood.deliveryapp.Repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exfood.deliveryapp.Entity.Customerdetailsentity;

@Repository
public interface Customerdetarepo extends JpaRepository<Customerdetailsentity, Long> {

    List<Customerdetailsentity> findByRestaurantNameOrderByIdDesc(String restaurantName);

    List<Customerdetailsentity> findByPhonenumberOrderByIdDesc(long phonenumber);

    List<Customerdetailsentity> findAllByOrderByIdDesc();
}
