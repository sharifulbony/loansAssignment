package com.shariful.loan.services;

import com.shariful.loan.configurations.Constants;
import com.shariful.loan.dtos.Approver;
import com.shariful.loan.interfaces.ApproverInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
@Service
public class ApproverService implements ApproverInterface {

    @Autowired
    public ApproverService() {
    }

    @Override
    public ArrayList<Approver> create(String approvers) {
        String[] approverList = approvers.split(Constants.approversSplitterSymbol);
        ArrayList<Approver> listOfApprovers = new ArrayList<>();
        for (String name : approverList
        ) {
            Approver approver = Approver.builder()
                    .name(name)
                    .timestamp(System.currentTimeMillis())
                    .decision(Constants.decisions.PENDING.name())
                    .build();
            listOfApprovers.add(approver);
        }
        return listOfApprovers;

    }
}
