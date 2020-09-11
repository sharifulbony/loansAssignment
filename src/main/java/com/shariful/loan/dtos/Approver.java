package com.shariful.loan.dtos;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Approver {
    private String name;
    private String decision;
    private Long timestamp;
    private Long decisionTimestamp;
}
