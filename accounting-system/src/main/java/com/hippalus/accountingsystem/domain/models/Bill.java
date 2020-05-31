package com.hippalus.accountingsystem.domain.models;


import com.hippalus.accountingsystem.domain.commands.BillCreateCommand;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.hippalus.accountingsystem.domain.models.BillState.*;
import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Bill {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // special to this requirement
    private Product product;

    @NotNull
    private String billNo;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PurchasingSpecialist purchasingSpecialist;

    @Enumerated(EnumType.ORDINAL)
    private BillState state= WAITING_APPROVE;

    @Builder
    private Bill(Long id, Product product, String billNo, PurchasingSpecialist purchasingSpecialist, BillState state) {
        checkArguments(product, billNo, purchasingSpecialist);
        this.id = id;
        this.product = product;
        this.billNo = billNo;
        this.state=state;
        this.purchasingSpecialist = purchasingSpecialist;
    }

    public static Bill create(BillCreateCommand cmd) {
        return Bill.builder()
                .product(Product.create(cmd.getProduct()))
                .billNo(cmd.getBillNo())
                .purchasingSpecialist(PurchasingSpecialist.create(cmd.getPurchasingSpecialist()))
                .build();

    }

    public Money totalPrice() {
        return this.product.getPrice();
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

    private void checkArguments(Product product, String billNo, PurchasingSpecialist purchasingSpecialist) {
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
}