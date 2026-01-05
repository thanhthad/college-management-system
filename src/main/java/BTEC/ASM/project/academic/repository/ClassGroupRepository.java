package BTEC.ASM.project.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BTEC.ASM.project.academic.entity.ClassGroup;

import java.util.Optional;

@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {

    Optional<ClassGroup> findByGroupName(String groupName);

    boolean existsByGroupName(String groupName);
}
