package com.hippalus.accountingsystem.application.web.resources;

import com.hippalus.accountingsystem.application.requests.BillSaveRequest;
import com.hippalus.accountingsystem.application.requests.SearchBillByFilterRequest;
import com.hippalus.accountingsystem.application.responses.BillResponse;
import com.hippalus.accountingsystem.application.responses.PurchasingSpecialistResponse;
import com.hippalus.accountingsystem.application.services.BillService;
import com.hippalus.accountingsystem.application.services.PurchasingSpecialistService;
import com.hippalus.accountingsystem.domain.models.PurchasingSpecialist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountingResource {
    private final PurchasingSpecialistService specialistService;
    private final BillService  billService;

    @PostMapping("/api/v1/bills")
    public ResponseEntity<PurchasingSpecialistResponse> addBill(@Valid @RequestBody BillSaveRequest request) throws URISyntaxException {
        final var billResponse = specialistService.addBill(request);
        return ResponseEntity
                .created(new URI("/api/v1/bills/" + billResponse.getId()))
                .body(billResponse);
    }

    @GetMapping("/api/v1/bills/{id}")
    public ResponseEntity<BillResponse> getBill(@PathVariable Long id) {
        return ResponseEntity.ok(billService.findById(id));
    }

    @GetMapping("/api/v1/bills")
    public ResponseEntity<List<BillResponse>> getAllBills() {
        return ResponseEntity.ok(billService.findAllBills());
    }

    @PostMapping("/api/v1/bills/search")
    public ResponseEntity<List<BillResponse>> getBillsByFilter(@RequestBody SearchBillByFilterRequest request) {
        return ResponseEntity.ok(billService.findByFilter(request));
    }

}
