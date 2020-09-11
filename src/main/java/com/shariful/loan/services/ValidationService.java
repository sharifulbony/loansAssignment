package com.shariful.loan.services;

import com.shariful.loan.configurations.Constants;
import com.shariful.loan.dtos.Input;
import com.shariful.loan.interfaces.ValidationInterface;
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
    public void validate(Input input)
            throws IllegalArgumentException, MissingServletRequestParameterException {
        validateKeys(input);
        validateCustomerId(input.getCustomerId());
        validateAmount(input.getAmount());
        validateApprover(input.getApprovers());
    }

    private void validateKeys(Input input)
            throws MissingServletRequestParameterException {
        if (input.getCustomerId() == null) {
            throw new MissingServletRequestParameterException(Constants.customerIdVarName,Constants.customerIdVarType);
        }
        if (input.getApprovers() == null) {
            throw new MissingServletRequestParameterException(Constants.approverVarName,Constants.approverVarType);
        }
        if (input.getAmount() == null) {
            throw new MissingServletRequestParameterException(Constants.amountVarName,Constants.amountVarType);
        }
    }

    private void validateApprover(String approvers)
            throws IllegalArgumentException {
        if (approvers.equals("")) {
            throw new IllegalArgumentException(Constants.minimumApproverInvalidMessage);
        }
        String[] approverList = approvers.split(Constants.approversSplitterSymbol);
        if (approverList.length < Constants.minAmountOfApprovers) {
            throw new IllegalArgumentException(Constants.minimumApproverInvalidMessage);
        } else if (approverList.length > Constants.maxAmountOfApprovers) {
            throw new IllegalArgumentException(Constants.maximumApproverInvalidMessage);
        }
    }

    private void validateAmount(Double amount)
            throws IllegalArgumentException {
        if (amount < Constants.minLoanAmount) {
            throw new IllegalArgumentException(Constants.amountInvalidMessage);
        }
    }

    private void validateCustomerId(String customerId)
            throws IllegalArgumentException {
        if (customerId.length() == Constants.customerIdLength) {
            String[] idParts = customerId.split(Constants.customerIdSplitterSymbol);
            if (idParts.length == Constants.customerIdParts) {
                if (isOnlyNumberAndCharacter(idParts[0], Constants.customerIdIstPartLength)
                        && isOnlyNumberAndCharacter(idParts[1], Constants.customerIdSecondPartLength)
                        && isOnlyNumberAndCharacter(idParts[2], Constants.customerIdThirdPartLength)
                ) {
                } else {
                    throw new IllegalArgumentException(Constants.customerIdNotAllowedCharacterMessage);
                }
            } else {
                throw new IllegalArgumentException(Constants.customerIdInvalidStructureMessage);
            }
        } else {
            throw new IllegalArgumentException(Constants.customerIdInvalidLengthMessage);
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
