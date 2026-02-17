package BTEC.ASM.project.modules.identity.service.impl;

import BTEC.ASM.project.modules.identity.entity.RefreshToken;
import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenExpiredException;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenNotFoundException;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenRevokedException;
import BTEC.ASM.project.modules.identity.repository.RefreshTokenRepository;
import BTEC.ASM.project.modules.identity.security.jwt.JwtUtil;
import BTEC.ASM.project.modules.identity.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtUtil jwtUtil;
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
        if(token == null || token.isBlank()){
            throw new RefreshTokenNotFoundException("Refresh token not found");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found"));

        if (refreshToken.isRevoked()) {
            throw new RefreshTokenRevokedException("Refresh token revoked");
        }

        if (refreshToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenExpiredException("Refresh token expired");
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

    public String generateAccessToken(String refreshToken){
        RefreshToken token = verify(refreshToken);
        User user = token.getUser();
        List<String> roles = user.getUserRoles()
                .stream()
                .map(ur -> ur.getRole().getRoleCode())
                .toList();

        String newAccessToken = jwtUtil.generateAccessToken(
                user.getId(),
                user.getUserCode(),
                roles
        );
        return newAccessToken;
    }

    /**
     * Revoke refresh token
     */
    public void revoke(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return;
        }

        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
    }

}
