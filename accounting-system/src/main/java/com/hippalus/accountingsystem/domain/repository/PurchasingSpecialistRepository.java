package com.hippalus.accountingsystem.domain.repository;

import com.hippalus.accountingsystem.domain.models.PurchasingSpecialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchasingSpecialistRepository extends JpaRepository<PurchasingSpecialist,Long> {
    Optional<PurchasingSpecialist> findByEmail(String email);
}
