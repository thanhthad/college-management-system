package BTEC.ASM.project.modules.academic.repository;

import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {

    /* =========================
     * FIND
     * ========================= */

    Optional<ClassGroup> findByGroupName(String groupName);

    Page<ClassGroup> findAll(Pageable pageable);

    /* =========================
     * EXISTS (VALIDATION)
     * ========================= */

    boolean existsByGroupName(String groupName);

    boolean existsByGroupNameAndIdNot(String groupName, Long id);

    /* =========================
     * SEARCH (FILTER)
     * ========================= */

    Page<ClassGroup> findByGroupNameContainingIgnoreCase(
            String groupName,
            Pageable pageable
    );

    Page<ClassGroup> findByCampusCode(
            String campusCode,
            Pageable pageable
    );

    Page<ClassGroup> findByDepartmentCode(
            String departmentCode,
            Pageable pageable
    );

    Page<ClassGroup> findByCampusCodeAndDepartmentCode(
            String campusCode,
            String departmentCode,
            Pageable pageable
    );

    /* =========================
     * ADVANCED SEARCH
     * ========================= */

    Page<ClassGroup> findByGroupNameContainingIgnoreCaseAndCampusCode(
            String groupName,
            String campusCode,
            Pageable pageable
    );

    Page<ClassGroup> findByGroupNameContainingIgnoreCaseAndDepartmentCode(
            String groupName,
            String departmentCode,
            Pageable pageable
    );
}