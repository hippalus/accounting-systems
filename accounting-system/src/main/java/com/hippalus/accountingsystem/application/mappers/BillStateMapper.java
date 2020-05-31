package com.hippalus.accountingsystem.application.mappers;

import com.hippalus.accountingsystem.domain.models.BillState;
import org.springframework.util.StringUtils;

import static com.hippalus.accountingsystem.domain.models.BillState.*;

public interface BillStateMapper {

    static BillState billStateToEnum(String strState) {
        if (StringUtils.isEmpty(strState)) {
            return null;
        }
        BillState state;
        switch (strState) {
            case "WAITING_APPROVE":
                state = WAITING_APPROVE;
                break;
            case "APPROVED":
                state = APPROVED;
                break;
            case "UNAPPROVED":
                state = UNAPPROVED;
                break;
            default:
                throw new IllegalArgumentException("Unexpected enum constant: " + strState);
        }
        return state;
    }

}