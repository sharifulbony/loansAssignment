package com.shariful.loan;
import com.shariful.loan.dtos.*;
import com.shariful.loan.services.*;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MissingServletRequestParameterException;
import java.util.OptionalDouble;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceTest {
    static ApproverService approverService;
    static LoanCreatorService loanCreatorService;
    static CustomerService customerService;
    static LoanProcessService loanProcessService;
    static ValidationService validationService;
    static ReporterService reporterService;
    static Input testCase1;
    static Input testCase2;
    static Input testCase3;
    static Input testCase4;
    static Input testCase5;
    static Input testCase6;

    @BeforeClass
    public static void init(){
        approverService=new ApproverService();
        loanCreatorService=new LoanCreatorService(approverService);
        customerService=new CustomerService(loanCreatorService);
        loanProcessService=new LoanProcessService(customerService);
        validationService=new ValidationService();
        reporterService=new ReporterService();
        testCase1=new Input("12-1234-123", (double) 200,"a,b");
        testCase2=new Input("12-1234-123", (double) 100,"a,b,c");
        testCase3 =new Input("12-124-123", (double) 100,"a,b,c");
        testCase4=new Input("12-1234-123", (double) -100,"a,b,c");
        testCase5=new Input("12-1234-123", (double) 100,"a,b,c,d");
        testCase6=new Input("12-1234-123", (double) 100,null);
    }
    @Test
    public void checkNewCustomerLoanInsert() {
        Data.allData.clear();
        loanProcessService.insert(testCase1);
        assertEquals(Data.allData.get(testCase1.getCustomerId()).getId(),testCase1.getCustomerId());
        assertEquals (Data.allData.get(testCase1.getCustomerId()).getCurrent().getAmount(),testCase1.getAmount());
        assertEquals(Data.allData.get(testCase1.getCustomerId()).getCurrent().getApprovers().size(),2);
    }
    @Test
    public void insertWithPendingCustomerLoan() {
        Data.allData.clear();
        loanProcessService.insert(testCase1);
        assertThrows(IllegalStateException.class, () ->loanProcessService.insert(testCase2));
    }
    @Test
    public void insertWithExistingCustomer() {
        Data.allData.clear();
        loanProcessService.insert(testCase1);
        Customer customer=Data.allData.get(testCase1.getCustomerId());
        loanProcessService.process(customer.getId(),customer.getCurrent().getApprovers().get(0).getName(),true);
        loanProcessService.process(customer.getId(),customer.getCurrent().getApprovers().get(1).getName(),true);
        loanProcessService.insert(testCase2);
        assertEquals(Data.allData.get(testCase2.getCustomerId()).getId(),testCase2.getCustomerId());
        assertEquals (Data.allData.get(testCase2.getCustomerId()).getCurrent().getAmount(),testCase2.getAmount());
        assertEquals(Data.allData.get(testCase2.getCustomerId()).getCurrent().getApprovers().size(),3);
    }
    @Test
    public void checkCurrentAfterApproval() {
        Data.allData.clear();
        loanProcessService.insert(testCase1);
        Customer customer=Data.allData.get(testCase1.getCustomerId());
        loanProcessService.process(customer.getId(),customer.getCurrent().getApprovers().get(0).getName(),true);
        loanProcessService.process(customer.getId(),customer.getCurrent().getApprovers().get(1).getName(),true);
        assertEquals(Data.allData.get(testCase1.getCustomerId()).getCurrent(),null);
    }
    @Test
    public void checkCurrentAfterRejection() {
        Data.allData.clear();
        loanProcessService.insert(testCase1);
        Customer customer=Data.allData.get(testCase1.getCustomerId());
        loanProcessService.process(customer.getId(),customer.getCurrent().getApprovers().get(0).getName(),false);
        assertEquals(Data.allData.get(testCase1.getCustomerId()).getCurrent(),null);
    }
    @SneakyThrows
    @Test
    public void inputValidationInvalidCustomerIdTest(){
        assertThrows(IllegalArgumentException.class, () ->validationService.validate(testCase3));;
    }
    @SneakyThrows
    @Test
    public void inputValidationInvalidAmountTest(){
        assertThrows(IllegalArgumentException.class, () ->validationService.validate(testCase4));
    }
    @SneakyThrows
    @Test
    public void inputValidationInvalidApproversTest(){
        assertThrows(IllegalArgumentException.class, () ->validationService.validate(testCase5));
    }
    @SneakyThrows
    @Test
    public void inputValidationParameterNotPresentTest(){
        assertThrows(MissingServletRequestParameterException.class, () ->validationService.validate(testCase6));
    }
    @Test
    public void reportTest(){
        Data.allData.clear();
        Data.approvedLoans.clear();
        loanProcessService.insert(testCase1);
        Customer customer=Data.allData.get(testCase1.getCustomerId());
        loanProcessService.process(customer.getId(),customer.getCurrent().getApprovers().get(0).getName(),true);
        loanProcessService.process(customer.getId(),customer.getCurrent().getApprovers().get(1).getName(),true);
        loanProcessService.insert(testCase2);
        loanProcessService.process(Data.allData.get(testCase2.getCustomerId()).getId(),
                Data.allData.get(testCase1.getCustomerId()).getCurrent().getApprovers().get(0).getName(),true);
        loanProcessService.process(Data.allData.get(testCase1.getCustomerId()).getId(),
                Data.allData.get(testCase1.getCustomerId()).getCurrent().getApprovers().get(1).getName(),true);
        loanProcessService.process(Data.allData.get(testCase1.getCustomerId()).getId(),
                Data.allData.get(testCase1.getCustomerId()).getCurrent().getApprovers().get(2).getName(),true);
        Report report =reporterService.getStatistics();
        Long count= Long.valueOf(2);
        Double sum=300.0;
        OptionalDouble avg= OptionalDouble.of(150.0);
        OptionalDouble max= OptionalDouble.of(200.0);
        OptionalDouble min= OptionalDouble.of(100.0);
        assertEquals(report.getCount(),count);
        assertEquals(report.getSum(), sum);
        assertEquals(report.getAvg(),avg);
        assertEquals(report.getMax(),max);
        assertEquals(report.getMin(),min);
    }
}
