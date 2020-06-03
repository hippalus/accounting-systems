package com.hippalus.accountingsystem.application.mappers;


import com.hippalus.accountingsystem.application.responses.BillResponse;
import com.hippalus.accountingsystem.application.responses.ProductResponse;
import com.hippalus.accountingsystem.application.responses.PurchasingSpecialistResponse;
import com.hippalus.accountingsystem.domain.models.Bill;
import com.hippalus.accountingsystem.domain.models.Product;
import com.hippalus.accountingsystem.domain.models.PurchasingSpecialist;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;


@Component
public class    PurchasingSpecialistMapperImpl implements PurchasingSpecialistMapper {

    @Override
    public PurchasingSpecialistResponse purSpecToPurSpecRes(PurchasingSpecialist entity) {
        if (entity == null) {
            return null;
        } else {
            var purchasingSpecialistResponse = PurchasingSpecialistResponse.builder();
            purchasingSpecialistResponse.firstName(entity.getFirstName());
            purchasingSpecialistResponse.lastName(entity.getLastName());
            purchasingSpecialistResponse.bills(this.billSetToBillResponseSet(entity.getBills()));
            purchasingSpecialistResponse.id(entity.getId());
            purchasingSpecialistResponse.email(entity.getEmail());
            return purchasingSpecialistResponse.build();
        }
    }

    @Override
    public PurchasingSpecialist purSpecResToPurSpec(PurchasingSpecialistResponse response) {
        if (response == null) {
            return null;
        } else {
            var purchasingSpecialist = PurchasingSpecialist.builder();
            purchasingSpecialist.firstName(response.getFirstName());
            purchasingSpecialist.lastName(response.getLastName());
            purchasingSpecialist.bills(this.billResponseSetToBillSet(response.getBills()));
            purchasingSpecialist.id(response.getId());
            purchasingSpecialist.email(response.getEmail());
            return purchasingSpecialist.build();
        }
    }

    protected Set<Bill> billResponseSetToBillSet(Set<BillResponse> bills) {
        if (bills == null) {
            return Collections.emptySet();
        } else {
            return bills.stream()
                    .map(this::billResponseToBill)
                    .collect(Collectors.toSet());
        }

    }

    protected Bill billResponseToBill(BillResponse response) {
        if (response == null) {
            return null;
        } else {
            var builder = Bill.builder();
            builder.id(response.getId());
            builder.billNo(response.getBillNo());
            builder.product(ProductMapper.productResToProduct(response.getProduct()));
            builder.state(BillStateMapper.billStateToEnum(response.getState()));
            return builder.build();
        }
    }

    protected ProductResponse productToProductResponse(Product product) {
        return ProductMapper.productToProductRes(product);
    }

    protected BillResponse billToBillResponse(Bill bill) {
        if (bill == null) {
            return null;
        } else {
            var billResponse = BillResponse.builder();
            billResponse.id(bill.getId());
            billResponse.billNo(bill.getBillNo());
            if (bill.getState() != null) {
                billResponse.state(bill.getState().name());
            }

            billResponse.product(this.productToProductResponse(bill.getProduct()));
            return billResponse.build();
        }
    }

    protected Set<BillResponse> billSetToBillResponseSet(Set<Bill> set) {
        if (set == null) {
            return Collections.emptySet();
        } else {
            return set.stream()
                    .map(this::billToBillResponse)
                    .collect(Collectors.toSet());
        }
    }
}
