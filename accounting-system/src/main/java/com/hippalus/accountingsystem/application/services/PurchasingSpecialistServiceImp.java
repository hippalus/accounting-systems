package com.hippalus.accountingsystem.application.services;

import com.hippalus.accountingsystem.application.mappers.BillStateMapper;
import com.hippalus.accountingsystem.application.requests.BillSaveRequest;
import com.hippalus.accountingsystem.application.requests.SearchBillByFilterRequest;
import com.hippalus.accountingsystem.application.responses.PurchasingSpecialistResponse;
import com.hippalus.accountingsystem.application.mappers.PurchasingSpecialistMapper;
import com.hippalus.accountingsystem.domain.commands.BillCreateCommand;
import com.hippalus.accountingsystem.domain.commands.ProductCreateCommand;
import com.hippalus.accountingsystem.domain.commands.PurchasingSpecialistCreateCommand;
import com.hippalus.accountingsystem.domain.commands.SearchBillCommand;
import com.hippalus.accountingsystem.domain.models.Bill;
import com.hippalus.accountingsystem.domain.models.PurchasingSpecialist;
import com.hippalus.accountingsystem.domain.repository.PurchasingSpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static org.springframework.transaction.annotation.Isolation.*;


@Service
@RequiredArgsConstructor
public class PurchasingSpecialistServiceImp implements PurchasingSpecialistService {

    private final PurchasingSpecialistRepository repository;
    private final PurchasingSpecialistMapper purchasingSpecialistMapper;


    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public Optional<PurchasingSpecialistResponse> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(purchasingSpecialistMapper::purSpecToPurSpecRes);
    }

    @Override
    public PurchasingSpecialistMapper getMapper() {
        return this.purchasingSpecialistMapper;
    }

    @Override
    @Transactional
    public PurchasingSpecialistResponse addBill(BillSaveRequest request) {

        final var billCreateCommand = createRequestToCreateCommand(request);
        var newBill = Bill.create(billCreateCommand);
        var purchasingSpecialist = repository.findByEmail(request.getEmail());
        if (purchasingSpecialist.isPresent()) {
            purchasingSpecialist.get().addBill(newBill);
            return  purchasingSpecialistMapper.purSpecToPurSpecRes(repository.saveAndFlush(purchasingSpecialist.get()));
        }else {
            final var purSpecCmd = billCreateCommand.getPurchasingSpecialist();
            final var purchasingSpec = PurchasingSpecialist.create(purSpecCmd);
            purchasingSpec.addBill(newBill);
           return purchasingSpecialistMapper.purSpecToPurSpecRes(repository.saveAndFlush(purchasingSpec));
        }
    }



    private BillCreateCommand createRequestToCreateCommand(BillSaveRequest request) {
        final var product = getProductCreateCommand(request);
        final var purchasingSpecialist = getPurchasingSpecialistCreateCommand(request);
        return BillCreateCommand.builder()
                .billNo(request.getBillNo())
                .product(product)
                .purchasingSpecialist(purchasingSpecialist)
                .build();
    }

    private ProductCreateCommand getProductCreateCommand(BillSaveRequest request) {
        return ProductCreateCommand.builder()
                .name(request.getProductName())
                .price(request.getPrice())
                .build();
    }

    private PurchasingSpecialistCreateCommand getPurchasingSpecialistCreateCommand(BillSaveRequest request) {
        return PurchasingSpecialistCreateCommand.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }

}
