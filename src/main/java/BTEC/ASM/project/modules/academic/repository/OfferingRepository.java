package BTEC.ASM.project.modules.academic.repository;

import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import BTEC.ASM.project.modules.academic.entity.Subject;
import BTEC.ASM.project.modules.academic.entity.Term;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BTEC.ASM.project.modules.academic.entity.Offering;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long> {

    boolean existsByTermId(Long id);

    boolean existsBySubjectId(Long id);

    boolean existsByClassGroupId(Long id);

    // =========================================================
    // 1. UNIQUE VALIDATION (Subject + Term + ClassGroup)
    // =========================================================

    // Check tồn tại offering theo bộ 3 (create)
    boolean existsBySubjectAndTermAndClassGroup(
            Subject subject,
            Term term,
            ClassGroup classGroup
    );

    // Check tồn tại offering theo bộ 3 (update - loại trừ chính nó)
    boolean existsBySubjectAndTermAndClassGroupAndIdNot(
            Subject subject,
            Term term,
            ClassGroup classGroup,
            Long id
    );

    // =========================================================
    // 2. DETAIL QUERY (single record)
    // =========================================================

    // Lấy offering theo bộ 3 khóa nghiệp vụ
    Optional<Offering> findBySubjectAndTermAndClassGroup(
            Subject subject,
            Term term,
            ClassGroup classGroup
    );

    // =========================================================
    // 3. PAGINATION – FILTER THEO 1 ĐIỀU KIỆN
    // =========================================================

    Page<Offering> findAllBySubject(
            Subject subject,
            Pageable pageable
    );

    Page<Offering> findAllByTerm(
            Term term,
            Pageable pageable
    );

    Page<Offering> findAllByClassGroup(
            ClassGroup classGroup,
            Pageable pageable
    );

    Page<Offering> findAllByStatus(
            String status,
            Pageable pageable
    );

    // =========================================================
    // 4. PAGINATION – FILTER THEO 2 ĐIỀU KIỆN
    // =========================================================

    Page<Offering> findAllBySubjectAndTerm(
            Subject subject,
            Term term,
            Pageable pageable
    );

    Page<Offering> findAllBySubjectAndClassGroup(
            Subject subject,
            ClassGroup classGroup,
            Pageable pageable
    );

    Page<Offering> findAllByTermAndClassGroup(
            Term term,
            ClassGroup classGroup,
            Pageable pageable
    );

    Page<Offering> findAllByTermAndStatus(
            Term term,
            String status,
            Pageable pageable
    );

    Page<Offering> findAllByClassGroupAndStatus(
            ClassGroup classGroup,
            String status,
            Pageable pageable
    );

    Page<Offering> findAllBySubjectAndStatus(
            Subject subject,
            String status,
            Pageable pageable
    );

    // =========================================================
    // 5. PAGINATION – FILTER THEO 3 ĐIỀU KIỆN
    // =========================================================

    Page<Offering> findAllByTermAndClassGroupAndStatus(
            Term term,
            ClassGroup classGroup,
            String status,
            Pageable pageable
    );

    Page<Offering> findAllBySubjectAndTermAndStatus(
            Subject subject,
            Term term,
            String status,
            Pageable pageable
    );

    Page<Offering> findAllBySubjectAndClassGroupAndStatus(
            Subject subject,
            ClassGroup classGroup,
            String status,
            Pageable pageable
    );

    // =========================================================
    // 6. PAGINATION – DATE / TIME BUSINESS
    // =========================================================

    // Offering đang active (today nằm trong startDate - endDate)
    Page<Offering> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate todayStart,
            LocalDate todayEnd,
            Pageable pageable
    );

    // Offering của term chưa kết thúc
    Page<Offering> findAllByTermAndEndDateAfter(
            Term term,
            LocalDate date,
            Pageable pageable
    );
}