package com.shariful.loan.dtos;
import lombok.*;

import java.util.OptionalDouble;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reporter {
    private Long count;
    private Double sum;
    private OptionalDouble avg= OptionalDouble.of(0.0);
    private OptionalDouble max=OptionalDouble.of(0.0);
    private OptionalDouble min=OptionalDouble.of(0.0);
}
