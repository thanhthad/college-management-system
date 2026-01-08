package BTEC.ASM.project.modules.identity.service;

import BTEC.ASM.project.modules.identity.entity.RefreshToken;
import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private static final long REFRESH_TOKEN_DAYS = 7;

    /**
     * Create new refresh token
     */
    public RefreshToken create(User user) {
        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .token(UUID.randomUUID().toString())
                        .user(user)
                        .expiredAt(LocalDateTime.now().plusDays(REFRESH_TOKEN_DAYS))
                        .revoked(false)
                        .build()
        );
    }

    /**
     * Verify refresh token by token string
     */
    public RefreshToken verify(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Refresh token revoked");
        }

        if (refreshToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    /**
     * Find valid refresh token of user (optional)
     */
    public Optional<RefreshToken> findOptionalValidByUser(User user) {
        return refreshTokenRepository
                .findFirstByUserAndRevokedFalseAndExpiredAtAfterOrderByExpiredAtDesc(
                        user, LocalDateTime.now()
                );
    }

    /**
     * Revoke refresh token
     */
    public void revoke(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
}
