package com.hippalus.accountingsystem.domain.models;

import com.hippalus.accountingsystem.domain.commands.PurchasingSpecialistCreateCommand;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.StringUtils.isEmpty;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchasing_specialist", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class PurchasingSpecialist {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Email
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bill> bills = new HashSet<>();

    @Transient//TODO Parameterize And Configurable
    private Money limit=Money.of(BigDecimal.valueOf(200));

    @Builder
    private PurchasingSpecialist(Long id, String firstName, String lastName, String email, Set<Bill> bills) {
        checkArguments(firstName, lastName, email);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bills = bills;
    }


    public static PurchasingSpecialist create(PurchasingSpecialistCreateCommand cmd) {
        return PurchasingSpecialist.builder()
                .email(cmd.getEmail())
                .firstName(cmd.getFirstName())
                .lastName(cmd.getLastName())
                .build();
    }

    public Money calculateTotalPriceOfApprovedBills() {
        return bills.stream()
                .filter(Bill::isApproved)
                .map(Bill::totalPrice)
                .reduce(Money.ZERO, Money::add);
    }

    public void addBill(Bill bill) {
        if (this.calculateTotalPriceOfApprovedBills()
                .add(bill.totalPrice())
                .isLessThan(this.limit)) {
            bill.approve();
        } else {
            bill.unApprove();
        }
    }

    private void checkArguments(String firstName, String lastName, String email) {
        if (isEmpty(firstName)) {
            throw new IllegalArgumentException("First name is required.");
        }
        if (isEmpty(lastName)) {
            throw new IllegalArgumentException("Last name is required.");
        }
        if (isEmpty(email)) {
            throw new IllegalArgumentException("E-mail is required.");
        }
    }
}
