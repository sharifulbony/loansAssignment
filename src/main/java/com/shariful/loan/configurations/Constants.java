package com.shariful.loan.configurations;

import java.util.zip.DeflaterOutputStream;

public class Constants {

    //region policy variable definition
    public static final int customerIdLength=11;
    public static final int customerIdParts=3;
    public static final int customerIdIstPartLength=2;
    public static final int customerIdSecondPartLength=4;
    public static final int customerIdThirdPartLength=3;
    public static final String customerIdSplitterSymbol="-";
    public static final String approversSplitterSymbol=",";
    public static final int maxAmountOfApprovers=3;
    public static final int minAmountOfApprovers=1;
    public static final String successMessage="Request Accepted!";
    public static final double minLoanAmount=0;
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
    public static final String requestLoanDescription="These Method Will create a loan request for a customer with the necessary data given as input";
    public static final String requestLoanParamDescription="These method will take  \'customerId\' in this pattern \'XX-XXXX-XXX\' a positive \'amount\' as loan amount and comma separated max 3 list of \'approvers\' ";

    public static final String requestLoanApprovalDescription="These Method Will add an approval to a pending loan request for a specified customer with the necessary data given as input";
    public static final String requestLoanCustomerIdParamDescription="These method will take  \'customerId\' in this pattern \'XX-XXXX-XXX\' ";
    public static final String requestLoanUsernameParamDescription=" \'username\' of the decision taking manager";
    public static final String requestLoanRejectionlDescription="These Method Will add an rejection to a pending loan request for a specified customer with the necessary data given as input. the loan request will be rejected as a result";

    public static final String requestStatisticsDescription="These Method Will return statistics of the specified time period that have been configured in the application.Default is 60 seconds.";

    //endregion
}
