package com.hippalus.accountingsystem.application.responses;

import lombok.*;
import javax.validation.constraints.NotNull;
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
    private ProductResponse product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillResponse bill = (BillResponse) o;

        if (!Objects.equals(id, bill.id)) return false;
        if (!Objects.equals(product, bill.product)) return false;
        if (!Objects.equals(billNo, bill.billNo)) return false;
        return state.equals(bill.state);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
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
                .add("product=" + product)
                .toString();
    }
}
