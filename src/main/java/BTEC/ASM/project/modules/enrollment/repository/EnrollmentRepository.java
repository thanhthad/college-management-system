package BTEC.ASM.project.modules.enrollment.repository;

import BTEC.ASM.project.modules.enrollment.entity.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    Page<Enrollment> findAll(Pageable pageable);

}
