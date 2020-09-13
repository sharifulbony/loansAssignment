package com.shariful.loan.configurations;

public class Constants {
    //region policy variable definition
    public static final long reportTimeInterval=60;
    public static final int customerIdLength=11;
    public static final int customerIdParts=3;
    public static final int customerIdIstPartLength=2;
    public static final int customerIdSecondPartLength=4;
    public static final int customerIdThirdPartLength=3;
    public static final String customerIdSplitterSymbol="-";
    public static final String approversSplitterSymbol=",";
    public static final int maxAmountOfApprovers=3;
    public static final int minAmountOfApprovers=1;
    public static final double minLoanAmount=0;
    public static final String customerIdVarName="customerId";
    public static final String customerIdVarType="String";
    public static final String amountVarName="amount";
    public static final String amountVarType="Double";
    public static final String approverVarName="approvers";
    public static final String approverVarType="String";
    public enum decisions{
            PENDING,APPROVED,REJECTED;
    }
    //endregion

    //region url definition
    public static final String baseURL="loan";
    public static final String loanRequestURL="request-approval";
    public static final String loanApproveURL="approve";
    public static final String loanRejectURL="reject";
    public static final String statisticsURL="statistics";
    //endregion

    //region api description constants
    public static final String baseURLDescription="Loan Management System";
    public static final String requestLoanDescription="These Method Will create a loan request for a customer with the necessary data given as input";
    public static final String requestLoanParamDescription="These method will take  \'customerId\' in this pattern \'XX-XXXX-XXX\' a positive \'amount\' as loan amount and comma separated max 3 list of \'approvers\' ";
    public static final String requestLoanApprovalDescription="These Method Will add an approval to a pending loan request for a specified customer with the necessary data given as input";
    public static final String requestLoanCustomerIdParamDescription="These method will take  \'customerId\' in this pattern \'XX-XXXX-XXX\' ";
    public static final String requestLoanUsernameParamDescription=" \'username\' of the decision taking manager";
    public static final String requestLoanRejectionlDescription="These Method Will add an rejection to a pending loan request for a specified customer with the necessary data given as input. the loan request will be rejected as a result";
    public static final String requestStatisticsDescription="These Method Will return statistics of the specified time period that have been configured in the application.Default is 60 seconds.";
    //endregion

    //region validation Messages#
    public static final String minimumApproverInvalidMessage="Request must have at least one approver!";
    public static final String maximumApproverInvalidMessage="Request must have at most three approvers!";
    public static final String amountInvalidMessage="Amount must be positive!";
    public static final String customerIdNotAllowedCharacterMessage="Customer ID have none allowed characters! only number and letters are allowed!";
    public static final String customerIdInvalidStructureMessage="Customer ID doesn't have required structure!";
    public static final String customerIdInvalidLengthMessage="Customer ID doesn't have required length!";
    public static final String httpStatusOkMessage="Successfully processed!";
    public static final String httpStatusIllegalInputMessage="Illegal input in request!";
    public static final String httpStatusBadRequestMessage="Bad request, adjust before retrying!";
    public static final String httpStatusNotAcceptableMessage="Input parameter not present!";
    //endregion
}
