package com.exfood.deliveryapp.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exfood.deliveryapp.Entity.Deliveryboyentity;
import com.exfood.deliveryapp.Repo.Deliveryboyrepo;
import com.exfood.deliveryapp.Security.PasswordUtil;

@Service
public class Deliveryboyser {

    @Autowired
    private Deliveryboyrepo deliveryRepo;

    public Deliveryboyentity register(Deliveryboyentity boy) {
        if (boy.getEmail() == null || boy.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (boy.getPhone() == null || boy.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone is required");
        }

        String cleanEmail = boy.getEmail().trim().toLowerCase();
        if (deliveryRepo.existsByEmail(cleanEmail)) {
            throw new IllegalStateException("A delivery account with this email already exists");
        }
        if (deliveryRepo.existsByPhone(boy.getPhone().trim())) {
            throw new IllegalStateException("A delivery account with this phone already exists");
        }

        boy.setEmail(cleanEmail);
        boy.setPhone(boy.getPhone().trim());
        boy.setPassword(PasswordUtil.hashPassword(boy.getPassword().trim()));
        boy.setRole("DELIVERY_BOY");
        boy.setStatus("Available");
        boy.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Deliveryboyentity saved = deliveryRepo.save(boy);
        saved.setPassword(null);
        return saved;
    }

    public Deliveryboyentity login(String identifier, String password) {
        if (identifier == null || password == null) return null;

        String cleanId = identifier.trim();
        Optional<Deliveryboyentity> boyOpt = deliveryRepo.findByEmail(cleanId.toLowerCase());
        if (boyOpt.isEmpty()) {
            boyOpt = deliveryRepo.findByPhone(cleanId);
        }

        if (boyOpt.isPresent()) {
            Deliveryboyentity boy = boyOpt.get();
            if (PasswordUtil.verifyPassword(password.trim(), boy.getPassword())) {
                Deliveryboyentity safe = new Deliveryboyentity();
                safe.setId(boy.getId());
                safe.setName(boy.getName());
                safe.setEmail(boy.getEmail());
                safe.setPhone(boy.getPhone());
                safe.setStatus(boy.getStatus());
                safe.setRole("DELIVERY_BOY");
                return safe;
            }
        }
        return null;
    }

    public List<Deliveryboyentity> getAllAvailable() {
        return deliveryRepo.findAll().stream().map(b -> {
            Deliveryboyentity safe = new Deliveryboyentity();
            safe.setId(b.getId());
            safe.setName(b.getName());
            safe.setEmail(b.getEmail());
            safe.setStatus(b.getStatus());
            safe.setRole("DELIVERY_BOY");
            return safe;
        }).collect(Collectors.toList());
    }

    public boolean updateStatus(Long id, String status) {
        Optional<Deliveryboyentity> opt = deliveryRepo.findById(id);
        if (opt.isPresent()) {
            Deliveryboyentity boy = opt.get();
            boy.setStatus(status);
            deliveryRepo.save(boy);
            return true;
        }
        return false;
    }
}
