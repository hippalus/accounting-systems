package com.hippalus.accountingsystem.application.services;

import com.hippalus.accountingsystem.application.mappers.PurchasingSpecialistMapper;
import com.hippalus.accountingsystem.application.requests.BillSaveRequest;
import com.hippalus.accountingsystem.application.responses.PurchasingSpecialistResponse;
import com.hippalus.accountingsystem.domain.models.PurchasingSpecialist;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Isolation.*;

public interface PurchasingSpecialistService {

    @Transactional(isolation = REPEATABLE_READ)
    Optional<PurchasingSpecialistResponse> findByEmail(String email);

    PurchasingSpecialistMapper getMapper();

    @Transactional
    PurchasingSpecialistResponse addBill(BillSaveRequest request);
}
