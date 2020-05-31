package com.hippalus.accountingsystem.application.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillSaveRequest {
    @NotNull
    private String billNo;

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @Email(message = "Please enter valid email")
    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Product name is required")
    private String productName;

    @NotNull(message = "Product price is required")
    @PositiveOrZero(message = "Product price cannot be less than zero")
    private BigDecimal price;

}
