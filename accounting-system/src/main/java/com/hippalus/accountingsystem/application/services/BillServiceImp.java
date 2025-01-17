package com.hippalus.accountingsystem.application.services;

import com.hippalus.accountingsystem.application.exceptions.DataNotFoundException;
import com.hippalus.accountingsystem.application.mappers.BillMapper;
import com.hippalus.accountingsystem.application.mappers.BillStateMapper;
import com.hippalus.accountingsystem.application.requests.SearchBillByFilterRequest;
import com.hippalus.accountingsystem.application.responses.BillResponse;
import com.hippalus.accountingsystem.domain.commands.SearchBillCommand;
import com.hippalus.accountingsystem.domain.repository.BillRepository;
import com.hippalus.accountingsystem.domain.specifications.BillSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Service
@RequiredArgsConstructor
public class BillServiceImp implements BillService {

    private final BillRepository repository;
    private final BillMapper billMapper;

    @Override
    public List<BillResponse> findByFilter(SearchBillByFilterRequest request) {
        final var searchBillCommand = searchReqToSearchCommand(request);
        return repository.findAll(new BillSpecification(searchBillCommand)).stream()
                .map(this.billMapper::billToBillResponse)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public List<BillResponse> findAllBills() {
        return repository.findAll().stream()
                .map(billMapper::billToBillResponse)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public BillResponse findById(Long id) {
        return repository.findById(id)
                .map(billMapper::billToBillResponse)
                .orElseThrow(() -> new DataNotFoundException("Bill not found"));
    }

    private SearchBillCommand searchReqToSearchCommand(SearchBillByFilterRequest request) {
        return SearchBillCommand.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .state(BillStateMapper.billStateToEnum(request.getState()))
                .build();
    }
}
