package com.hippalus.accountingsystem.application.mappers;

import com.hippalus.accountingsystem.application.responses.BillResponse;
import com.hippalus.accountingsystem.domain.models.Bill;

public interface BillMapper {

    BillResponse billToBillResponse(Bill entity);

    Bill billResponseToBill(BillResponse response);
}
