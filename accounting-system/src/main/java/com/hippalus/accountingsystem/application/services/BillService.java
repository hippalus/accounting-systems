package com.hippalus.accountingsystem.application.services;

import com.hippalus.accountingsystem.application.requests.BillSaveRequest;
import com.hippalus.accountingsystem.application.requests.SearchBillByFilterRequest;
import com.hippalus.accountingsystem.application.responses.BillResponse;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.springframework.transaction.annotation.Isolation.*;

public interface BillService {

    @Transactional
    BillResponse save(BillSaveRequest request);

    @Transactional(isolation = REPEATABLE_READ)
    List<BillResponse> findByFilter(SearchBillByFilterRequest request);

    @Transactional(isolation = REPEATABLE_READ)
    List<BillResponse> findAllBills();



}
