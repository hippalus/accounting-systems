package com.hippalus.accountingsystem.domain.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchasingSpecialistCreateCommand {
    private final String firstName;
    private final String lastName;
    private final String email;

}
