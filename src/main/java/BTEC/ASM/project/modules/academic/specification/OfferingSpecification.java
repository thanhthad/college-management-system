package BTEC.ASM.project.modules.academic.specification;


import BTEC.ASM.project.modules.academic.dto.request.AdminOfferingFilter;
import BTEC.ASM.project.modules.academic.dto.request.OfferingFilter;
import BTEC.ASM.project.modules.academic.entity.Offering;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OfferingSpecification {

    public static Specification<Offering> byClientFilter(OfferingFilter filter) {
        return new Specification<Offering>() {

            @Override
            public Predicate toPredicate(
                    Root<Offering> root,
                    CriteriaQuery<?> query,
                    CriteriaBuilder cb
            ) {

                List<Predicate> predicates = new ArrayList<>();

                if (filter.status() != null) {
                    Predicate statusPredicate =
                            cb.equal(root.get("status"), filter.status());
                    predicates.add(statusPredicate);
                }

                if (filter.subjectCode() != null) {
                    Predicate subjectPredicate =
                            cb.equal(
                                    root.get("subject").get("subjectCode"),
                                    filter.subjectCode()
                            );
                    predicates.add(subjectPredicate);
                }

                if (filter.termCode() != null) {
                    Predicate termPredicate =
                            cb.equal(
                                    root.get("term").get("termCode"),
                                    filter.termCode()
                            );
                    predicates.add(termPredicate);
                }

                if (filter.classGroupName() != null) {
                    Predicate classGroupPredicate =
                            cb.equal(
                                    root.get("classGroup").get("groupName"),
                                    filter.classGroupName()
                            );
                    predicates.add(classGroupPredicate);
                }

                if (filter.startDate() != null) {
                    Predicate startDatePredicate =
                            cb.greaterThanOrEqualTo(
                                    root.get("startDate"),
                                    filter.startDate()
                            );
                    predicates.add(startDatePredicate);
                }

                if (filter.endDate() != null) {
                    Predicate endDatePredicate =
                            cb.lessThanOrEqualTo(
                                    root.get("endDate"),
                                    filter.endDate()
                            );
                    predicates.add(endDatePredicate);
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public static Specification<Offering> byAdminFilter(AdminOfferingFilter filter) {
        return new Specification<Offering>() {

            @Override
            public Predicate toPredicate(
                    Root<Offering> root,
                    CriteriaQuery<?> query,
                    CriteriaBuilder cb
            ) {

                List<Predicate> predicates = new ArrayList<>();

                if (filter.status() != null) {
                    Predicate statusPredicate =
                            cb.equal(root.get("status"), filter.status());
                    predicates.add(statusPredicate);
                }

                if (filter.subjectId() != null) {
                    Predicate subjectPredicate =
                            cb.equal(
                                    root.get("subject").get("id"),
                                    filter.subjectId()
                            );
                    predicates.add(subjectPredicate);
                }

                if (filter.termId() != null) {
                    Predicate termPredicate =
                            cb.equal(
                                    root.get("term").get("id"),
                                    filter.termId()
                            );
                    predicates.add(termPredicate);
                }

                if (filter.classGroupId() != null) {
                    Predicate classGroupPredicate =
                            cb.equal(
                                    root.get("classGroup").get("id"),
                                    filter.classGroupId()
                            );
                    predicates.add(classGroupPredicate);
                }

                if (filter.startDate() != null) {
                    Predicate startDatePredicate =
                            cb.greaterThanOrEqualTo(
                                    root.get("startDate"),
                                    filter.startDate()
                            );
                    predicates.add(startDatePredicate);
                }

                if (filter.endDate() != null) {
                    Predicate endDatePredicate =
                            cb.lessThanOrEqualTo(
                                    root.get("endDate"),
                                    filter.endDate()
                            );
                    predicates.add(endDatePredicate);
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
