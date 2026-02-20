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

    /* =========================
     * LOOKUP / QUERY 1 RECORD
     * ========================= */

    // Dùng khi: update, detail, mapping FK
    Optional<Term> findByTermCode(String termCode);

    /* =========================
     * EXISTS (VALIDATION)
     * ========================= */

    // Dùng khi: create / update để check trùng
    boolean existsByTermCode(String termCode);

    /* =========================
     * LIST / FILTER
     * ========================= */

    // Lấy danh sách term theo thời gian
    Page<Term> findByStartDateAfter(LocalDate date, Pageable pageable);

    Page<Term> findByEndDateBefore(LocalDate date, Pageable pageable);

    // Lấy các term đang active (current term)
    Page<Term> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate now1,
            LocalDate now2,
            Pageable pageable
    );

    /* =========================
     * SORT (rất hay dùng)
     * ========================= */

    Page<Term> findAll(Pageable pageable);
//
//    // Lấy danh sách term mới nhất
//    List<Term> findAllByOrderByStartDateDesc();
}
