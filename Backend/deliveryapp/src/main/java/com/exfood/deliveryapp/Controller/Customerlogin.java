package com.exfood.deliveryapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exfood.deliveryapp.Entity.Customerlogentity;
import com.exfood.deliveryapp.Services.Customerloginser;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/Customerlogin")
@CrossOrigin(origins = "*")
public class Customerlogin {
    @Autowired 
    Customerloginser cl;

    @PostMapping("/login")
    public String postMethodName(@RequestBody Customerlogentity e) {
        
        cl.insertdata(e);
        return "inserted";
    }
    
    
    
}
