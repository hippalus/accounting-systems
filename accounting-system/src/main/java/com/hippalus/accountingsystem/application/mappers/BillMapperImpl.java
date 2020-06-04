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
public class BillMapperImpl implements BillMapper {

    @Override
    public BillResponse billToBillResponse(Bill entity) {
        if (entity == null) {
            return null;
        } else {
            var billResponse = BillResponse.builder();
            billResponse.products(entity.getProducts().stream()
                    .map(this::productToProductResponse)
                    .collect(Collectors.toList()));
            billResponse.id(entity.getId());
            billResponse.billNo(entity.getBillNo());
            if (entity.getState() != null) {
                billResponse.state(entity.getState().name());
            }

            return billResponse.build();
        }
    }

    @Override
    public Bill billResponseToBill(BillResponse response) {
        if (response == null) {
            return null;
        } else {
            var builder = Bill.builder();
            builder.id(response.getId());
            builder.billNo(response.getBillNo());
            builder.products(response.getProducts().stream()
                    .map(ProductMapper::productResToProduct)
                    .collect(Collectors.toList()));
            return builder.build();
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

    protected PurchasingSpecialistResponse purchasingSpecialistToPurchasingSpecialistResponse(PurchasingSpecialist purchasingSpecialist) {
        if (purchasingSpecialist == null) {
            return null;
        } else {
            var purchasingSpecialistResponse = PurchasingSpecialistResponse.builder();
            purchasingSpecialistResponse.id(purchasingSpecialist.getId());
            purchasingSpecialistResponse.firstName(purchasingSpecialist.getFirstName());
            purchasingSpecialistResponse.lastName(purchasingSpecialist.getLastName());
            purchasingSpecialistResponse.email(purchasingSpecialist.getEmail());
            purchasingSpecialistResponse.bills(this.billSetToBillResponseSet(purchasingSpecialist.getBills()));
            return purchasingSpecialistResponse.build();
        }
    }

    public PurchasingSpecialist purchasingSpecialistResponseToPurchasingSpecialist(PurchasingSpecialistResponse response) {
        if (response == null) {
            return null;
        } else {
            var purchasingSpecialist = PurchasingSpecialist.builder();
            purchasingSpecialist.id(response.getId());
            purchasingSpecialist.firstName(response.getFirstName());
            purchasingSpecialist.lastName(response.getLastName());
            purchasingSpecialist.email(response.getEmail());
            purchasingSpecialist.bills(this.billResponseSetToBillSet(response.getBills()));
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

    protected ProductResponse productToProductResponse(Product product) {
        return ProductMapper.productToProductRes(product);
    }

}
