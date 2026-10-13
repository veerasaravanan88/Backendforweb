package com.exfood.deliveryapp.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exfood.deliveryapp.Entity.Customerdetailsentity;

public interface  Customerdetarepo extends JpaRepository<Customerdetailsentity,Long> {
   
}
