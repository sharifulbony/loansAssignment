package com.shariful.loan.configurations;

import java.util.zip.DeflaterOutputStream;

public class Constants {

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

    public static enum decisions{
            PENDING,APPROVED,REJECTED;
    }


    public static final String baseURL="loan";
    public static final String loanRequestURL="request-approval";
    public static final String loanApproveURL="approve";
    public static final String loanRejectURL="reject";
    public static final String statisticsURL="statistics";


}
