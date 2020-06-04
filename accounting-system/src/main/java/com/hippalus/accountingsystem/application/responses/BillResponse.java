package com.hippalus.accountingsystem.application.responses;

import lombok.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse {

    private Long id;
    @NotNull
    private String billNo;

    private String state;
    @NotNull
    @Builder.Default
    private List<ProductResponse> products =new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillResponse bill = (BillResponse) o;

        if (!Objects.equals(id, bill.id)) return false;
        if (!Objects.equals(products, bill.products)) return false;
        if (!Objects.equals(billNo, bill.billNo)) return false;
        return state.equals(bill.state);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (products != null ? products.hashCode() : 0);
        result = 31 * result + (billNo != null ? billNo.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BillResponse.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("billNo='" + billNo + "'")
                .add("state='" + state + "'")
                .add("product=" + products)
                .toString();
    }
}
