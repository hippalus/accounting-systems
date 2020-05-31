package com.hippalus.accountingsystem.domain.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillCreateCommand {
    private final ProductCreateCommand product;
    private final String billNo;
    private final PurchasingSpecialistCreateCommand purchasingSpecialist;
}
