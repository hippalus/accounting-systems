package com.hippalus.accountingsystem.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hippalus.accountingsystem.ApplicationStartupConfig;
import com.hippalus.accountingsystem.domain.commands.PurchasingSpecialistCreateCommand;
import lombok.*;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import static org.springframework.util.StringUtils.isEmpty;

@Getter
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

    @OneToMany(mappedBy = "purchasingSpecialist",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private Set<Bill> bills = new HashSet<>();

    @Transient
    private static Money limit;

    public void setLimit(){
        limit=ApplicationStartupConfig.LIMIT;
    }
    @Builder
    private PurchasingSpecialist(Long id, String firstName, String lastName, String email, Set<Bill> bills) {
        setLimit();
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
                .bills(new HashSet<>())
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
                .isLessThan(limit)) {
            bill.approve();

        } else {
            bill.unApprove();
        }
        bills.add(bill);
        bill.setPurchasingSpecialist(this);
    }
    public void removeBill(Bill bill){
        bills.remove(bill);
        bill.setPurchasingSpecialist(null);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasingSpecialist that = (PurchasingSpecialist) o;
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
        return new StringJoiner(", ", PurchasingSpecialist.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .toString();
    }
}
