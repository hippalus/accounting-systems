package com.hippalus.accountingsystem.application.mappers;

import com.hippalus.accountingsystem.application.responses.PurchasingSpecialistResponse;
import com.hippalus.accountingsystem.domain.models.PurchasingSpecialist;

public interface PurchasingSpecialistMapper {

    PurchasingSpecialistResponse purSpecToPurSpecRes(PurchasingSpecialist entity);

    PurchasingSpecialist purSpecResToPurSpec(PurchasingSpecialistResponse response);
}

