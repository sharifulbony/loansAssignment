package com.shariful.loan.configurations;

import com.shariful.loan.dtos.Customer;
import com.shariful.loan.dtos.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    public DataSeeder(

    ) {
    }


    @Override
    public void run(String... args) throws Exception {
//
//        List<Customer> customerList=new ArrayList<>();
        Customer customer=Customer.builder().id("12-1234-123").build();
        Data.allData.put(customer.getId(),customer);




    }


}
