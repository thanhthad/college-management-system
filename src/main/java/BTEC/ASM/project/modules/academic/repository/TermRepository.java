package BTEC.ASM.project.modules.academic.repository;

import BTEC.ASM.project.modules.academic.entity.Term;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    Optional<Term> findByTermCode(String termCode);

    boolean existsByTermCode(String termCode);

    Page<Term> findByStartDateAfter(LocalDate date, Pageable pageable);

    Page<Term> findByEndDateBefore(LocalDate date, Pageable pageable);

    Page<Term> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate now1,
            LocalDate now2,
            Pageable pageable
    );

    Page<Term> findAll(Pageable pageable);

    boolean existsByTermCodeAndIdNot(String termCode, Long id);

}
