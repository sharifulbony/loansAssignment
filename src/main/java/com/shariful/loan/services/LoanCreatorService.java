package com.shariful.loan.services;

import com.shariful.loan.configurations.Constants;
import com.shariful.loan.dtos.Input;
import com.shariful.loan.dtos.Loan;
import com.shariful.loan.interfaces.LoanCreatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanCreatorService implements LoanCreatorInterface {
    private ApproverService approverService=new ApproverService();

    @Autowired
    public LoanCreatorService() {

    }

    @Override
    public Loan create(Input input){
        Loan loan=Loan.builder()
                .amount(input.getAmount())
                .timestamp(System.currentTimeMillis())
                .status(Constants.decisions.PENDING.name())
                .approvers(approverService.create(input.getApprovers()))
                .build();
        return loan;

    }
}
