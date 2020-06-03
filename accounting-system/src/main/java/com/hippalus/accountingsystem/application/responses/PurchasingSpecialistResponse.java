package com.hippalus.accountingsystem.application.responses;

import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchasingSpecialistResponse {
    private Long id;
    @NotNull(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    private String lastName;
    @Email(message = "Please enter valid email")
    @NotNull(message = "Email is required")
    private String email;
    @JsonIgnore
    @Builder.Default
    private Set<BillResponse> bills =new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasingSpecialistResponse that = (PurchasingSpecialistResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PurchasingSpecialistResponse.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
               .add("bills=" + bills)
                .toString();
    }
}
