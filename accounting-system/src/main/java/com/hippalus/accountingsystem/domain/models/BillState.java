package com.hippalus.accountingsystem.domain.models;


public enum BillState {
    WAITING_APPROVE( 0),
    APPROVED(1),
    UNAPPROVED( 2);


    private final int intValue;

    BillState(int intValue) {
        this.intValue = intValue;
    }
    public int getIntValue() {
        return intValue;
    }
}