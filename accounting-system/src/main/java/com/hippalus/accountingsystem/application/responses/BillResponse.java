package com.hippalus.accountingsystem.application.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse {

    private Long id;
    @NotNull
    private String billNo;

    private String state;
    @NotNull
    private ProductResponse product;
    @NotNull
    private PurchasingSpecialistResponse purchasingSpecialist;
}
