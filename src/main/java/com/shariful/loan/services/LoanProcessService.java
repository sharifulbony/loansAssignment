package com.shariful.loan.services;
import com.shariful.loan.configurations.Constants;
import com.shariful.loan.dtos.*;
import com.shariful.loan.interfaces.CustomerInterface;
import com.shariful.loan.interfaces.LoanProcessorInterface;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanProcessService implements LoanProcessorInterface {
    private final CustomerInterface customerService;

    public LoanProcessService(CustomerInterface customerService) {
        this.customerService = customerService;
    }

    @Override
    public void insert(Input input) throws IllegalArgumentException {
        if (checkExisting(input.getCustomerId())) {
            createForExistingCustomer(input);
        } else {
            createForNewCustomer(input);
        }
    }

    @Override
    public void process(String customerId, String managerName, boolean decision) throws IllegalArgumentException, IllegalStateException {
        Customer customer = Data.allData.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Invalid Customer Id!");
        } else {
            if (customer.getCurrent() != null) {
                processDecision(customer, managerName, decision);
            } else {
                throw new IllegalStateException("Customer Doesn't have any pending loans!");
            }
        }
    }

    private boolean checkExisting(String customerId) {
        if (Data.allData.get(customerId) != null) {
            return true;
        } else {
            return false;
        }
    }

    private void createForExistingCustomer(Input input) throws IllegalStateException {
        Customer customer = Data.allData.get(input.getCustomerId());
        customer = customerService.update(customer, input);
        Data.allData.put(customer.getId(), customer);
    }

    private void createForNewCustomer(Input input) {
        Customer customer = customerService.create(input);
        Data.allData.put(customer.getId(), customer);
    }

    private void processDecision(Customer customer, String managerName, boolean decision)
            throws IllegalArgumentException {
        Approver currentApprover = customer.getCurrent().getApprovers()
                .stream()
                .filter(manager -> manager.getName().equals(managerName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Provided manager username is not in approver List!"));
        if (!decision) {
            reject(currentApprover, customer);
        } else {
            approve(currentApprover, customer, managerName);
        }
    }

    private void reject(Approver approver, Customer customer) {
        Loan currentLoan = customer.getCurrent();
        approver.setDecision(Constants.decisions.REJECTED.name());
        approver.setDecisionTimestamp(System.currentTimeMillis());
        currentLoan.setStatus(Constants.decisions.REJECTED.name());
        customer.getHistory().add(currentLoan);
        customer.setCurrent(null);
    }

    private void approve(Approver currentApprover, Customer customer, String managerName) {
        currentApprover.setDecision(Constants.decisions.APPROVED.name());
        currentApprover.setDecisionTimestamp(System.currentTimeMillis());
        customer.getCurrent().setStatus(Constants.decisions.APPROVED.name());
        List<Approver> otherApprovers = customer.getCurrent().getApprovers()
                .stream()
                .filter(manager -> !manager.getName().equals(managerName))
                .collect(Collectors.toList());

        checkOtherApproval(otherApprovers, customer);
    }

    private void checkOtherApproval(List<Approver> otherApprovers, Customer customer) {
        List<Approver> pendingApprovers = otherApprovers
                .stream()
                .filter(manager -> manager.getDecision() == Constants.decisions.PENDING.name())
                .collect(Collectors.toList());
        if (pendingApprovers.size() == 0) {
            customer.getHistory().add(customer.getCurrent());
            Data.approvedLoans.add(customer.getCurrent());
            customer.setCurrent(null);
        }
    }
}
