package com.shariful.loan.dtos;
import java.util.*;

public class Data {
    public static Map<String, Customer> allData= Collections.synchronizedMap(new HashMap<>());
    public static List<Loan> approvedLoans= new ArrayList<>();
}
