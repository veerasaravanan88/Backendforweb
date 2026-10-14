package com.exfood.deliveryapp.Repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exfood.deliveryapp.Entity.Deliveryboyentity;

@Repository
public interface Deliveryboyrepo extends JpaRepository<Deliveryboyentity, Long> {

    Optional<Deliveryboyentity> findByEmail(String email);

    Optional<Deliveryboyentity> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
