package com.shariful.loan.services;

import com.shariful.loan.configurations.Constants;
import com.shariful.loan.dtos.Input;
import com.shariful.loan.interfaces.ValidationInterface;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.regex.Pattern;

@Service
public class ValidationService implements ValidationInterface {

    @Autowired
    public ValidationService() {
    }
    @Override
    public void validate(Input input) throws IllegalArgumentException, MissingServletRequestParameterException {

        validateKeys(input);
        validateCustomerId(input.getCustomerId());
        validateAmount(input.getAmount());
        validateApprover(input.getApprovers());
    }


    private void validateKeys(Input input) throws MissingServletRequestParameterException {
        if(input.getCustomerId()==null){
            throw new MissingServletRequestParameterException ("customerId","String");
        }
        if(input.getApprovers()==null){
            throw new MissingServletRequestParameterException ("approvers","String");
        }
        if(input.getAmount()==null){
            throw new MissingServletRequestParameterException ("amount","Double");
        }
    }
    private void validateApprover(String approvers) throws IllegalArgumentException {
        if (approvers.equals("")) {
            throw new IllegalArgumentException("Request must have at least one approver!");
        }
        String[] approverList = approvers.split(",");
        if (approverList.length < Constants.minAmountOfApprovers) {
            throw new IllegalArgumentException("Request must have at least one approver!");
        } else if (approverList.length > Constants.maxAmountOfApprovers) {
            throw new IllegalArgumentException("Request must have at most three approvers!");
        }

    }
    private void validateAmount(Double amount) throws IllegalArgumentException {
        if (amount < Constants.minLoanAmount) {
            throw new IllegalArgumentException("Amount must be positive!");
        }
    }
    private void validateCustomerId(String customerId) throws IllegalArgumentException {
        if (customerId.length() == Constants.customerIdLength) {
            String[] idParts = customerId.split(Constants.customerIdSplitterSymbol);
            if (idParts.length == Constants.customerIdParts) {
                if (isOnlyNumberAndCharacter(idParts[0], Constants.customerIdIstPartLength)
                        && isOnlyNumberAndCharacter(idParts[1], Constants.customerIdSecondPartLength)
                        && isOnlyNumberAndCharacter(idParts[2], Constants.customerIdThirdPartLength)
                ) {

                } else {
                    throw new IllegalArgumentException("Customer ID have none allowed characters! only number and letters are allowed.");
                }
            } else {
                throw new IllegalArgumentException("Customer ID doesn't have required structure");
            }

        } else {
            throw new IllegalArgumentException("Customer ID doesn't have required length!");

        }
    }
    private boolean isOnlyNumberAndCharacter(String input, int desiredLength) {
        if (Pattern.matches("[A-Za-z0-9]+", input) && input.length() == desiredLength) {
            return true;

        } else {
            return false;
        }

    }
}
