package BTEC.ASM.project.modules.identity.repository;

import BTEC.ASM.project.modules.identity.entity.UserRole;
import BTEC.ASM.project.modules.identity.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository
        extends JpaRepository<UserRole, UserRoleId> {

    List<UserRole> findByUser_Id(Long userId);

    boolean existsByUser_IdAndRole_RoleCode(Long userId, String roleCode);
}
