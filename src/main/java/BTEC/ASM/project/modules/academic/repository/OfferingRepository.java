package BTEC.ASM.project.modules.academic.repository;

import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import BTEC.ASM.project.modules.academic.entity.Subject;
import BTEC.ASM.project.modules.academic.entity.Term;
import BTEC.ASM.project.modules.academic.enums.OfferingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import BTEC.ASM.project.modules.academic.entity.Offering;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long>, JpaSpecificationExecutor<Offering> {

    boolean existsByTermId(Long id);

    boolean existsBySubjectId(Long id);

    boolean existsByClassGroupId(Long id);

    boolean existsBySubjectAndTermAndClassGroup(
            Subject subject,
            Term term,
            ClassGroup classGroup
    );

    Offering findBySubjectAndTermAndClassGroup(
            Subject subject,
            Term term,
            ClassGroup classGroup
    );

    boolean existsBySubjectAndTermAndClassGroupAndIdNot(
            Subject subject,
            Term term,
            ClassGroup classGroup,
            Long id
    );
}