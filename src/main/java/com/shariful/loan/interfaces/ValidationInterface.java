package com.shariful.loan.interfaces;
import com.shariful.loan.dtos.Input;
import org.springframework.web.bind.MissingServletRequestParameterException;

public interface ValidationInterface {
    void validate(Input input) throws IllegalArgumentException, MissingServletRequestParameterException;
}
