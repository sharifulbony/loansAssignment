package com.shariful.loan.dtos;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private String id;
    private Loan current;
    private List<Loan> history;
}
