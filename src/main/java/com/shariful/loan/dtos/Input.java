package com.shariful.loan.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Input {
    private String customerId;
    private Double amount;
    private String approvers;
}
