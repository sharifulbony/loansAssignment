package com.shariful.loan.controllers;

import com.shariful.loan.configurations.Constants;
import com.shariful.loan.dtos.ApiSuccess;
import com.shariful.loan.dtos.Data;
import com.shariful.loan.dtos.Input;
import com.shariful.loan.services.LoanProcessService;
import com.shariful.loan.services.ReporterService;
import com.shariful.loan.services.ValidationService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;


/**
 *
 */
@RestController
@RequestMapping(value = Constants.baseURL)
@Api(value = "Product Inventory System", description = "Operations related to Product Inventory Management System")

public class LoanController {

    ValidationService validation = new ValidationService();
    LoanProcessService loanProcessService = new LoanProcessService();
    ReporterService reporterService=new ReporterService();

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/all")
    public ResponseEntity<?> allData() {
        return ResponseEntity.ok(Data.allData);

    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/all-loans")
    public ResponseEntity<?> allApprovedLoans() {
        return ResponseEntity.ok(Data.approvedLoans);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = Constants.loanRequestURL, method = RequestMethod.POST)
//    @ApiOperation(value = DocumentationStaticContext.userRegisterDescription, response = Iterable.class)
    public ResponseEntity<?> incomingApprovalRequest(
//            @ApiParam(value = DocumentationStaticContext.userRegisterParam)
            @RequestBody Input input) throws IllegalArgumentException, MissingServletRequestParameterException {
        validation.validate(input);
        loanProcessService.insert(input);

        ApiSuccess apiSuccess= new ApiSuccess(HttpStatus.OK,Constants.successMessage);
        //synchronized thread-pool
        return ResponseEntity.ok(apiSuccess);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = Constants.loanApproveURL, method = RequestMethod.POST)
//    @ApiOperation(value = DocumentationStaticContext.userRegisterDescription, response = Iterable.class)
    public ResponseEntity<?> approve(@RequestParam String customerId, @RequestParam String username) throws IllegalArgumentException,IllegalStateException {
        loanProcessService.process(customerId, username, true);
        ApiSuccess apiSuccess= new ApiSuccess(HttpStatus.OK,Constants.successMessage);
        return ResponseEntity.ok(apiSuccess);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = Constants.loanRejectURL, method = RequestMethod.POST)
//    @ApiOperation(value = DocumentationStaticContext.userRegisterDescription, response = Iterable.class)
    public ResponseEntity<?> reject(@RequestParam String customerId, @RequestParam String username) throws IllegalArgumentException,IllegalStateException {
        loanProcessService.process(customerId, username, false);
        ApiSuccess apiSuccess= new ApiSuccess(HttpStatus.OK,Constants.successMessage);
        return ResponseEntity.ok(apiSuccess);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = Constants.statisticsURL)
    public ResponseEntity<?> statistics(){

        return ResponseEntity.ok(reporterService.getStatistics());

    }





}
