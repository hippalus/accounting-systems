package com.hippalus.accountingsystem.application.web.resources;

import com.hippalus.accountingsystem.application.requests.BillSaveRequest;
import com.hippalus.accountingsystem.application.requests.SearchBillByFilterRequest;
import com.hippalus.accountingsystem.application.responses.BillResponse;
import com.hippalus.accountingsystem.application.responses.PurchasingSpecialistResponse;
import com.hippalus.accountingsystem.application.services.BillService;
import com.hippalus.accountingsystem.application.services.PurchasingSpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounting")
@RequiredArgsConstructor
public class AccountingResource {
    private final PurchasingSpecialistService specialistService;
    private final BillService  billService;

    @PostMapping("/addbill")
    public ResponseEntity<PurchasingSpecialistResponse> addBill(@Valid @RequestBody BillSaveRequest request) throws URISyntaxException {
        final var billResponse = specialistService.addBill(request);
        return ResponseEntity
                .created(new URI("/api/v1/accounting/purchasingspecialist/" + billResponse.getId()))
                .body(billResponse);
    }

    @GetMapping("/purchasingspecialist/{id}")
    public ResponseEntity<PurchasingSpecialistResponse> getBillsByPurchasingSpecialist(@PathVariable Long id) {
        return ResponseEntity.ok(specialistService.findById(id));
    }

    @GetMapping("/purchasingspecialist")
    public ResponseEntity<List<PurchasingSpecialistResponse>> getAllPurchasingSpecialist() {
        return ResponseEntity.ok(specialistService.findAll());
    }


    @PostMapping("/bills/search")
    public ResponseEntity<List<BillResponse>> getBillsByFilter(@RequestBody SearchBillByFilterRequest request) {
        return ResponseEntity.ok(billService.findByFilter(request));
    }

    @GetMapping("/bills")
    public ResponseEntity<List<BillResponse>> getAllBills() {
        return ResponseEntity.ok(billService.findAllBills());
    }

}
