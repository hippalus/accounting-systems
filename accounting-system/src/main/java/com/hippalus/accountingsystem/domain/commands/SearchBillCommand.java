package com.hippalus.accountingsystem.domain.commands;

import com.hippalus.accountingsystem.domain.models.BillState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchBillCommand {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final BillState state;
}
