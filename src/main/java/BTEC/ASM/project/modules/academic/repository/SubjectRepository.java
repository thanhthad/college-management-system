package BTEC.ASM.project.modules.academic.repository;

import BTEC.ASM.project.modules.academic.entity.Term;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BTEC.ASM.project.modules.academic.entity.Subject;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findBySubjectCode(String subjectCode);

    boolean existsBySubjectCode(String subjectCode);

    boolean existsBySubjectCodeAndSubjectIdNot(String subjectCode, Long subjectId);

    Page<Subject> findBySubjectNameContainingIgnoreCase(
            String subjectName,
            Pageable pageable
    );

    Page<Subject> findAll(Pageable pageable);

}
