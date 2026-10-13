package com.exfood.deliveryapp.Services;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exfood.deliveryapp.Entity.Customerdetailsentity;
import com.exfood.deliveryapp.Repo.Customerdetarepo;

@Service 
public class Customerdetaservice {
    @Autowired 
    Customerdetarepo crd;
    public String insert(Customerdetailsentity e)
    {
        crd.save(e);
        return"inserted";
    }

    public List<Customerdetailsentity> getAllOrders() {

    return crd.findAll(Sort.by(Sort.Direction.DESC,"amount"));

}


public void updateStatus(long phonenumber, String status) {

    Customerdetailsentity order =
            crd.findById(phonenumber).orElse(null);

    if (order != null) {

        order.setStatus(status);

        crd.save(order);
    }
}

public boolean updateOrderStatus(long phonenumber, String newStatus) {
        Optional<Customerdetailsentity> optionalOrder = crd.findById(phonenumber);
        if (optionalOrder.isPresent()) {
            Customerdetailsentity order = optionalOrder.get();
            order.setStatus(newStatus);
            crd.save(order);
            return true;
        }
        return false;
    }


}
