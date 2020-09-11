package com.shariful.loan.interfaces;

import com.shariful.loan.dtos.Input;
import com.shariful.loan.dtos.Loan;

public interface LoanCreatorInterface {
    Loan create(Input input);
}
