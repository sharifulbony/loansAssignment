package com.shariful.loan.handlers;
import com.shariful.loan.dtos.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public GlobalExceptionHandler() {
        super();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleInvalidInput(
            IllegalArgumentException illegalArgumentException) {
        ApiError apiError = new ApiError();
        apiError.setMessage(illegalArgumentException.getMessage());
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setDebugMessage(illegalArgumentException.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<Object> handleIllegalRequest(
            IllegalStateException illegalStateException) {
        ApiError apiError = new ApiError();
        apiError.setMessage(illegalStateException.getMessage());
        apiError.setStatus(HttpStatus.NOT_ACCEPTABLE);
        apiError.setDebugMessage(illegalStateException.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException missingServletRequestParameterException,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setMessage(missingServletRequestParameterException.getMessage());
        apiError.setStatus(HttpStatus.EXPECTATION_FAILED);
        apiError.setDebugMessage(missingServletRequestParameterException.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}






