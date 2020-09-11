package com.shariful.loan.services;

import com.shariful.loan.dtos.Customer;
import com.shariful.loan.dtos.Input;
import com.shariful.loan.interfaces.CustomerInterface;
import com.shariful.loan.interfaces.LoanCreatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerService implements CustomerInterface {
    private final LoanCreatorInterface loanCreatorService;

    @Autowired
    public CustomerService(LoanCreatorInterface loanCreatorService) {

        this.loanCreatorService = loanCreatorService;
    }

    @Override
    public Customer create(Input input) {
        Customer customer = Customer.builder()
                .id(input.getCustomerId())
                .current(loanCreatorService.create(input))
                .history(new ArrayList<>())
                .build();
        return customer;

    }

    @Override
    public Customer update(Customer customer, Input input) throws IllegalStateException {
        if (checkPendingLoans(customer)) {
            Customer updatedCustomer = Customer.builder()
                    .id(customer.getId())
                    .current(loanCreatorService.create(input))
                    .history(customer.getHistory())
                    .build();
            return updatedCustomer;
        } else {
            throw new IllegalStateException("Customer already have pending loans!");
        }

    }

    private boolean checkPendingLoans(Customer customer) {
        if (customer.getCurrent() != null) {
            return false;
        } else {
            return true;
        }
    }


}
