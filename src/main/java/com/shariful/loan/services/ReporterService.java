package com.shariful.loan.services;
import com.shariful.loan.configurations.Constants;
import com.shariful.loan.dtos.Data;
import com.shariful.loan.dtos.Loan;
import com.shariful.loan.dtos.Report;
import com.shariful.loan.interfaces.ReporterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class ReporterService implements ReporterInterface {

    @Autowired
    public ReporterService() {
    }

    @Override
    public Report getStatistics() {
        List<Loan> reportingLoans = Data.approvedLoans
                .stream()
                .filter(t -> t.getTimestamp() >= System.currentTimeMillis() - (Constants.reportTimeInterval * 1000))
                .collect(Collectors.toList());
        if (reportingLoans.size() > 0) {
            Report report = Report.builder()
                    .count(returnCount(reportingLoans))
                    .sum(returnSum(reportingLoans))
                    .avg(returnAvg(reportingLoans))
                    .max(returnMax(reportingLoans))
                    .min(returnMin(reportingLoans))
                    .build();
            return report;
        } else {
            throw new IllegalArgumentException("No Data found on specified Range!");
        }
    }

    private Long returnCount(List<Loan> loans) {
        return (long) Data.approvedLoans
                .size();
    }

    private Double returnSum(List<Loan> loans) {
        return Data.approvedLoans
                .stream()
                .mapToDouble(Loan::getAmount)
                .sum();
    }

    private OptionalDouble returnAvg(List<Loan> loans) {
        return Data.approvedLoans
                .stream()
                .mapToDouble(Loan::getAmount)
                .average();
    }

    private OptionalDouble returnMax(List<Loan> loans) {
        return Data.approvedLoans
                .stream()
                .mapToDouble(Loan::getAmount)
                .max();
    }

    private OptionalDouble returnMin(List<Loan> loans) {
        return Data.approvedLoans
                .stream()
                .mapToDouble(Loan::getAmount)
                .min();
    }
}

