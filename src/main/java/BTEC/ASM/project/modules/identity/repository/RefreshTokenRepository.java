package BTEC.ASM.project.modules.identity.repository;

import BTEC.ASM.project.modules.identity.entity.RefreshToken;
import BTEC.ASM.project.modules.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken>
    findFirstByUserAndRevokedFalseAndExpiredAtAfterOrderByExpiredAtDesc(
            User user,
            LocalDateTime now
    );
}
