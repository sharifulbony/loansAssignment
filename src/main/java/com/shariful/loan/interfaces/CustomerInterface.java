package com.shariful.loan.interfaces;

import com.shariful.loan.dtos.Customer;
import com.shariful.loan.dtos.Input;

public interface CustomerInterface {
    Customer create(Input input);
    Customer update(Customer customer, Input input);

}
