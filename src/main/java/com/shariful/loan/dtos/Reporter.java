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
    private OptionalDouble avg;
    private OptionalDouble max;
    private OptionalDouble min;
}
