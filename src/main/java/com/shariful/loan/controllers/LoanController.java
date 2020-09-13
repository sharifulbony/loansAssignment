package com.shariful.loan.controllers;

import com.shariful.loan.configurations.Constants;
import com.shariful.loan.dtos.ApiError;
import com.shariful.loan.dtos.ApiSuccess;
import com.shariful.loan.dtos.Input;
import com.shariful.loan.dtos.Report;
import com.shariful.loan.interfaces.LoanProcessorInterface;
import com.shariful.loan.interfaces.ReporterInterface;
import com.shariful.loan.interfaces.ValidationInterface;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.baseURL)
@Api(value = Constants.baseURLDescription)
public class LoanController {
    private final ValidationInterface validationService;
    private final LoanProcessorInterface loanProcessService;
    private final ReporterInterface reporterService;

    public LoanController(ValidationInterface validationService,
                          LoanProcessorInterface loanProcessService,
                          ReporterInterface reporterService) {
        this.validationService = validationService;
        this.loanProcessService = loanProcessService;
        this.reporterService = reporterService;
    }

    /**
     * Method crates a loan application if the input is correct
     *
     * @param input customerId, the Id of the requested customer in defined structure
     * @param input approvers, comma separated list of approvers
     * @return Method returns error with http status code {@link ApiError} objects if loan has error.
     * @Param input  amount, positive requested loan amount
     */
    @CrossOrigin(origins = "*")
    @PostMapping(value = Constants.loanRequestURL)
    @ApiOperation(value = Constants.requestLoanDescription, response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.httpStatusOkMessage),
            @ApiResponse(code = 400, message = Constants.httpStatusBadRequestMessage),
            @ApiResponse(code = 406, message = Constants.httpStatusIllegalInputMessage),
            @ApiResponse(code = 417, message = Constants.httpStatusNotAcceptableMessage),
    })
    public ResponseEntity<?> incomingApprovalRequest(
            @ApiParam(value = Constants.requestLoanParamDescription)
            @RequestBody Input input)
            throws IllegalArgumentException, MissingServletRequestParameterException {
        validationService.validate(input);
        loanProcessService.insert(input);
        ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, Constants.httpStatusOkMessage);
        return ResponseEntity.ok(apiSuccess);
    }

    /**
     * Method add an approval to a existing loan application if the input is correct
     *
     * @param customerId,       the Id of the requested customer in defined structure
     * @param username,username of the manager
     * @return Method returns error with http status code {@link ApiError} objects if input has error.
     */
    @CrossOrigin(origins = "*")
    @PostMapping(value = Constants.loanApproveURL)
    @ApiOperation(value = Constants.requestLoanApprovalDescription, response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.httpStatusOkMessage),
            @ApiResponse(code = 400, message = Constants.httpStatusBadRequestMessage),
            @ApiResponse(code = 406, message = Constants.httpStatusIllegalInputMessage),
            @ApiResponse(code = 417, message = Constants.httpStatusNotAcceptableMessage),
    })
    public ResponseEntity<?> approve(
            @ApiParam(value = Constants.requestLoanCustomerIdParamDescription)
            @RequestParam String customerId,
            @ApiParam(value = Constants.requestLoanUsernameParamDescription)
            @RequestParam String username)
            throws IllegalArgumentException, IllegalStateException {
        loanProcessService.process(customerId, username, true);
        ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, Constants.httpStatusOkMessage);
        return ResponseEntity.ok(apiSuccess);
    }

    /**
     * Method add a rejection to a existing loan application if the input is correct
     *
     * @param customerId,       the Id of the requested customer in defined structure
     * @param username,username of the manager
     * @return Method returns error with http status code {@link ApiError} objects if input has error.
     */
    @CrossOrigin(origins = "*")
    @PostMapping(value = Constants.loanRejectURL)
    @ApiOperation(value = Constants.requestLoanRejectionlDescription, response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = Constants.httpStatusOkMessage),
            @ApiResponse(code = 400, message = Constants.httpStatusBadRequestMessage),
            @ApiResponse(code = 406, message = Constants.httpStatusIllegalInputMessage),
            @ApiResponse(code = 417, message = Constants.httpStatusNotAcceptableMessage),
    })
    public ResponseEntity<?> reject(
            @ApiParam(value = Constants.requestLoanCustomerIdParamDescription)
            @RequestParam String customerId,
            @ApiParam(value = Constants.requestLoanUsernameParamDescription)
            @RequestParam String username)
            throws IllegalArgumentException, IllegalStateException {
        loanProcessService.process(customerId, username, false);
        ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, Constants.httpStatusOkMessage);
        return ResponseEntity.ok(apiSuccess);
    }

    /**
     * Method returns statistics of the approved loan in a defined time frame
     *
     * @return Method returns error with http status code {@link ApiError} objects there is no data on specified time range.
     */
    @CrossOrigin(origins = "*")
    @GetMapping(value = Constants.statisticsURL)
    @ApiOperation(value = Constants.requestStatisticsDescription, response = Iterable.class)
    public ResponseEntity<?> statistics() {
        return ResponseEntity.ok(reporterService.getStatistics());
    }
}
