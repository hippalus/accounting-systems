package com.hippalus.accountingsystem.application.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchBillByFilterRequest {

    private String firstName;

    private String lastName;

    @Email(message = "Please enter valid email")
    private String email;

    private String state;

}
