package BTEC.ASM.project.academic.repository;

import BTEC.ASM.project.academic.entity.ClassGroup;
import BTEC.ASM.project.academic.entity.Subject;
import BTEC.ASM.project.academic.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BTEC.ASM.project.academic.entity.Offering;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long> {
    boolean existsBySubjectAndTermAndClassGroup(
            Subject subject,
            Term term,
            ClassGroup classGroup
    );
}
