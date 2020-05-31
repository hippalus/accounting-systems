package com.hippalus.accountingsystem.application.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    @NotNull(message = "Product name is required")
    private String name;
    @NotNull(message = "Product price is required")
    @PositiveOrZero(message = "Product price cannot be less than zero")
    private BigDecimal price;
}
