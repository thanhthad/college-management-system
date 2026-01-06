package BTEC.ASM.project.modules.identity.repository;

import BTEC.ASM.project.modules.identity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleCode(String roleCode);
}

