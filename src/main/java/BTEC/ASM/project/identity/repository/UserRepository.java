package BTEC.ASM.project.identity.repository;

import BTEC.ASM.project.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserCode(String userCode);

    boolean existsByUserCode(String userCode);
}
