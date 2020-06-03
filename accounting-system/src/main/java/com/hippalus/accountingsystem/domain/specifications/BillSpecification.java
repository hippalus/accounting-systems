package com.hippalus.accountingsystem.domain.specifications;

import com.hippalus.accountingsystem.domain.commands.SearchBillCommand;
import com.hippalus.accountingsystem.domain.models.Bill;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class BillSpecification implements Specification<Bill> {

    private static final String PURCHASING_SPECIALIST = "purchasingSpecialist";

    private final transient SearchBillCommand filter;

    @Override
    public Predicate toPredicate(Root<Bill> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getFirstName() != null) {
            predicates.add(cb.like(root.join(PURCHASING_SPECIALIST).get("firstName"),
                    filter.getFirstName()));
        }
        if (filter.getLastName() != null) {
            predicates.add(cb.like(root.join(PURCHASING_SPECIALIST).get("lastName"),
                    filter.getLastName()));
        }
        if (filter.getEmail() != null) {
            predicates.add(cb.equal(root.join(PURCHASING_SPECIALIST).get("email"),
                    filter.getEmail()));
        }
        if (filter.getState() != null) {
            predicates.add(cb.equal(root.get("state"), filter.getState()));
        }

       /*I like to add this because without it if no criteria is specified then
         everything is returned. Because that's how queries work without where
         clauses. However, if my user doesn't provide any criteria I want to
         say no results found. */
        if (predicates.isEmpty()) {
            predicates.add(cb.equal(root.get("id"), -1));

        }
        return cq.where(cb.and(predicates.toArray(new Predicate[0])))
                .distinct(true)
                //.orderBy(cb.desc(root.join(PURCHASING_SPECIALIST).get("firstName")))
                .getRestriction();
    }
}
