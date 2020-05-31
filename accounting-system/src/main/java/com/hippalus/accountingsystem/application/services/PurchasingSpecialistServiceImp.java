package com.hippalus.accountingsystem.application.services;

import com.hippalus.accountingsystem.application.responses.PurchasingSpecialistResponse;
import com.hippalus.accountingsystem.application.mappers.PurchasingSpecialistMapper;
import com.hippalus.accountingsystem.domain.repository.PurchasingSpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static org.springframework.transaction.annotation.Isolation.*;


@Service
@RequiredArgsConstructor
public class PurchasingSpecialistServiceImp implements PurchasingSpecialistService {

    private final PurchasingSpecialistRepository repository;
    private final PurchasingSpecialistMapper purchasingSpecialistMapper;

    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public Optional<PurchasingSpecialistResponse> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(purchasingSpecialistMapper::purSpecToPurSpecRes);
    }

    @Override
    public PurchasingSpecialistMapper getMapper() {
        return this.purchasingSpecialistMapper;
    }

}
