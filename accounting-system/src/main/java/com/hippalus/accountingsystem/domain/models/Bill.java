package com.hippalus.accountingsystem.domain.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hippalus.accountingsystem.domain.commands.BillCreateCommand;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.*;

import static com.hippalus.accountingsystem.domain.models.BillState.*;
import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bill", uniqueConstraints = {@UniqueConstraint(columnNames = {"billNo"})})
public class Bill {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @OneToMany(cascade ={CascadeType.ALL},fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Product> products =new ArrayList<>();

    @NotNull
    private String billNo;

    @Setter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private PurchasingSpecialist purchasingSpecialist;

    @Enumerated(EnumType.ORDINAL)
    private BillState state;

    @Builder
    private Bill(Long id, List<Product> products, String billNo, PurchasingSpecialist purchasingSpecialist, BillState state) {
        checkArguments(products, billNo, purchasingSpecialist);
        this.id = id;
        this.products = products;
        this.billNo = billNo;
        this.state=state;
        this.purchasingSpecialist = purchasingSpecialist;
    }

    public static Bill create(BillCreateCommand cmd) {
        Bill bill = Bill.builder()
                .billNo(cmd.getBillNo())
                .products(new ArrayList<>())
                .purchasingSpecialist(PurchasingSpecialist.create(cmd.getPurchasingSpecialist()))
                .state(WAITING_APPROVE)
                .build();
        bill.addProduct(Product.create(cmd.getProduct()));
        return bill;
    }

    public void addProduct(final Product product) {
        if(isNull(product)){
            return;
        }
        this.products.add(product);
    }


    public Money totalPrice() {
        return this.products.stream()
                .map(Product::getPrice)
                .reduce(Money.ZERO,Money::add);
    }

    public boolean isApproved() {
        return this.getState() == APPROVED;
    }

    public boolean isUnApproved() {
        return this.getState() == UNAPPROVED;
    }

    public void approve() {
        changeState(APPROVED);
    }

    public void unApprove() {
        changeState(UNAPPROVED);
    }

    private void changeState(BillState newState) {
        final var diff = newState.getIntValue() - this.getState().getIntValue();
        if (diff < 0) {
            throw new IllegalStateException(String.format("Bill State cannot be transferring  from %s to %s", this.getState(), newState));
        }
        this.state = newState;
    }

    private void checkArguments(List<Product> product, String billNo, PurchasingSpecialist purchasingSpecialist) {
        if (isNull(purchasingSpecialist)) {
            throw new IllegalArgumentException("Purchasing specialist is required.");
        }
        if (isEmpty(billNo)) {
            throw new IllegalArgumentException("Bill number is required.");
        }
        if (isNull(product)) {
            throw new IllegalArgumentException("Product is required.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        if (!Objects.equals(id, bill.id)) return false;
        if (!Objects.equals(products, bill.products)) return false;
        if (!Objects.equals(billNo, bill.billNo)) return false;
        if (!Objects.equals(purchasingSpecialist, bill.purchasingSpecialist))
            return false;
        return state == bill.state;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (products != null ? products.hashCode() : 0);
        result = 31 * result + (billNo != null ? billNo.hashCode() : 0);
        result = 31 * result + (purchasingSpecialist != null ? purchasingSpecialist.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Bill.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("product=" + products)
                .add("billNo='" + billNo + "'")
                .add("purchasingSpecialist=" + purchasingSpecialist)
                .add("state=" + state)
                .toString();
    }
}