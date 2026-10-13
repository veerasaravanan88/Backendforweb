package com.exfood.deliveryapp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exfood.deliveryapp.Entity.Customerlogentity;
import com.exfood.deliveryapp.Repo.Customerloginrepo;
@Service 
public class Customerloginser {
    @Autowired 
    Customerloginrepo clp;
    public String insertdata(Customerlogentity e)
    {
       clp.save(e);
     return "reg";
    }
}
