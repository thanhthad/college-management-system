package BTEC.ASM.project.modules.identity.service;
import BTEC.ASM.project.modules.identity.entity.RefreshToken;
import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(User user, String token) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiredAt(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validateRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenAndRevokedFalse(token)
                .orElseThrow(() ->
                        new RuntimeException("Refresh token không hợp lệ")
                );

        if (refreshToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token đã hết hạn");
        }

        return refreshToken;
    }

    public void revokeToken(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
}
