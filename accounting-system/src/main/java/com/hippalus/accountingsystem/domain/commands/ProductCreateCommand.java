package com.hippalus.accountingsystem.domain.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductCreateCommand {
    private final String name;
    private final BigDecimal price;
}
