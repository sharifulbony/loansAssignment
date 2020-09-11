package com.shariful.loan.interfaces;

import com.shariful.loan.dtos.Input;

public interface LoanProcessorInterface {
    void insert(Input input);
    void process(String customerId,String managerName,boolean decision);
}
