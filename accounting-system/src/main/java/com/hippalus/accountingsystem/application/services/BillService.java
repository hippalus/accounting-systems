package com.hippalus.accountingsystem.application.services;

import com.hippalus.accountingsystem.application.requests.SearchBillByFilterRequest;
import com.hippalus.accountingsystem.application.responses.BillResponse;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.springframework.transaction.annotation.Isolation.*;

public interface BillService {

    @Transactional(isolation = REPEATABLE_READ)
    List<BillResponse> findByFilter(SearchBillByFilterRequest request);

    @Transactional(isolation = REPEATABLE_READ)
    List<BillResponse> findAllBills();

    @Transactional(isolation = REPEATABLE_READ)
    BillResponse findById(Long id);
}
