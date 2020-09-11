package com.shariful.loan.controllers;

import com.shariful.loan.configurations.Constants;
import com.shariful.loan.dtos.ApiSuccess;
import com.shariful.loan.dtos.Data;
import com.shariful.loan.dtos.Input;
import com.shariful.loan.interfaces.LoanProcessorInterface;
import com.shariful.loan.interfaces.ReporterInterface;
import com.shariful.loan.interfaces.ValidationInterface;
import com.shariful.loan.services.LoanProcessService;
import com.shariful.loan.services.ReporterService;
import com.shariful.loan.services.ValidationService;
import io.swagger.annotations.*;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;


/**
 *
 */
@RestController
@RequestMapping(value = Constants.baseURL)
@Api(value = "Loan Management System")

public class LoanController {


    private final ValidationInterface validationService;
    private final LoanProcessorInterface loanProcessService ;
    private final ReporterInterface reporterService;

    public LoanController(ValidationInterface validationService, LoanProcessorInterface loanProcessService, ReporterInterface reporterService) {
        this.validationService = validationService;
        this.loanProcessService = loanProcessService;
        this.reporterService = reporterService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/all")
    public ResponseEntity<?> allData() {
        return ResponseEntity.ok(Data.allData);

    }
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/all-loans")
    public ResponseEntity<?> allApprovedLoans() {
        return ResponseEntity.ok(Data.approvedLoans);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = Constants.loanRequestURL)
    @ApiOperation(value = Constants.requestLoanDescription, response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully processed"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Illegal input in request"),
            @ApiResponse(code = 417, message = "Input parameter not present"),

    })
    public ResponseEntity<?> incomingApprovalRequest(
            @ApiParam(value = Constants.requestLoanParamDescription)
            @RequestBody Input input) throws IllegalArgumentException, MissingServletRequestParameterException {
        validationService.validate(input);
        loanProcessService.insert(input);

        ApiSuccess apiSuccess= new ApiSuccess(HttpStatus.OK,Constants.successMessage);
        //synchronized thread-pool
        return ResponseEntity.ok(apiSuccess);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = Constants.loanApproveURL)
    @ApiOperation(value = Constants.requestLoanApprovalDescription, response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully processed"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Illegal input in request"),
            @ApiResponse(code = 417, message = "Input parameter not present"),

    })
    public ResponseEntity<?> approve(@ApiParam(value = Constants.requestLoanCustomerIdParamDescription) @RequestParam String customerId, @ApiParam(value = Constants.requestLoanUsernameParamDescription)@RequestParam String username) throws IllegalArgumentException,IllegalStateException {
        loanProcessService.process(customerId, username, true);
        ApiSuccess apiSuccess= new ApiSuccess(HttpStatus.OK,Constants.successMessage);
        return ResponseEntity.ok(apiSuccess);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = Constants.loanRejectURL)
    @ApiOperation(value = Constants.requestLoanRejectionlDescription, response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully processed"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Illegal input in request"),
            @ApiResponse(code = 417, message = "Input parameter not present"),
    })
    public ResponseEntity<?> reject(@ApiParam(value = Constants.requestLoanCustomerIdParamDescription)@RequestParam String customerId, @ApiParam(value = Constants.requestLoanUsernameParamDescription)@RequestParam String username) throws IllegalArgumentException,IllegalStateException {
        loanProcessService.process(customerId, username, false);
        ApiSuccess apiSuccess= new ApiSuccess(HttpStatus.OK,Constants.successMessage);
        return ResponseEntity.ok(apiSuccess);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = Constants.statisticsURL)
    @ApiOperation(value = Constants.requestStatisticsDescription, response = Iterable.class)
    public ResponseEntity<?> statistics(){

        return ResponseEntity.ok(reporterService.getStatistics());

    }





}
