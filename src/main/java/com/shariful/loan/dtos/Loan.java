package com.shariful.loan.dtos;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {
    private Double amount;
    private Long timestamp;
    private String status;
    private List<Approver> approvers;
}
