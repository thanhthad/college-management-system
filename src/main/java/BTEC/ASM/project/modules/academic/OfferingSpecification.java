package BTEC.ASM.project.modules.academic;


import BTEC.ASM.project.modules.academic.dto.request.OfferingFilter;
import BTEC.ASM.project.modules.academic.entity.Offering;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OfferingSpecification {

    public static Specification<Offering> build(OfferingFilter f) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (f.getStatus() != null) {
                predicates.add(
                        cb.equal(root.get("status"), f.getStatus())
                );
            }

            if (f.getSubjectCode() != null) {
                predicates.add(
                        cb.equal(
                                root.get("subject").get("subjectCode"),
                                f.getSubjectCode()
                        )
                );
            }

            if (f.getTermCode() != null) {
                predicates.add(
                        cb.equal(
                                root.get("term").get("termCode"),
                                f.getTermCode()
                        )
                );
            }

            if (f.getClassGroupName() != null) {
                predicates.add(
                        cb.equal(
                                root.get("classGroup").get("groupName"),
                                f.getClassGroupName()
                        )
                );
            }

            if (f.getStartDateFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("startDate"),
                                f.getStartDateFrom()
                        )
                );
            }

            if (f.getEndDateTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("endDate"),
                                f.getEndDateTo()
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
