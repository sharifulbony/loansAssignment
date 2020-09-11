package com.shariful.loan.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter

public class ApiSuccess {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    public ApiSuccess() {
        timestamp = LocalDateTime.now();
    }

    public ApiSuccess(HttpStatus status, String message ) {
        this();
        this.status = status;
        this.message = message;
    }
}
