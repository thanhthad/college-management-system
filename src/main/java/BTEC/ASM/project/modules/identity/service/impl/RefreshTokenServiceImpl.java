package BTEC.ASM.project.modules.identity.service.impl;

import BTEC.ASM.project.common.utils.IpUtils;
import BTEC.ASM.project.modules.identity.entity.RefreshToken;
import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenExpiredException;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenNotFoundException;
import BTEC.ASM.project.modules.identity.exception.refresh_tokens.RefreshTokenRevokedException;
import BTEC.ASM.project.modules.identity.repository.RefreshTokenRepository;
import BTEC.ASM.project.modules.identity.security.jwt.JwtUtil;
import BTEC.ASM.project.modules.identity.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final long REFRESH_TOKEN_DAYS = 7;

    /**
     * Create new refresh token
     */
    public RefreshToken create(User user,String ip) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiredAt(LocalDateTime.now().plusDays(REFRESH_TOKEN_DAYS))
                .revoked(false)
                .build();

        RefreshToken saved = refreshTokenRepository.save(refreshToken);
        log.info("AUTH_EVENT | action=REFRESH_TOKEN_CREATED | userId={} | tokenId={} | ip={}",user.getId(),saved.getId(),ip);
        return saved;
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
    public RefreshToken findValidByUser(User user, String ip) {
        Optional<RefreshToken> token =  refreshTokenRepository
                .findFirstByUserAndRevokedFalseAndExpiredAtAfterOrderByExpiredAtDesc(
                        user, LocalDateTime.now()
                );
        if(token.get() == null){
            create(user,ip);
        }else{
            log.info("AUTH_EVENT | action=REFRESH_TOKEN_REFRESH | userId={} | tokenId={} | ip={}",token.get().getUser().getId(),token.get().getId(),ip);
        }
        return token.get();
    }

    public String generateAccessToken(String refreshToken, String ip){
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
        log.info("AUTH_EVENT | action=REFRESH_TOKEN_REFRESH | userId={} | tokenId={} | ip={}",token.getUser().getId(),token.getId(),ip);

        return newAccessToken;
    }

    /**
     * Revoke refresh token
     */
    public void revoke(String refreshToken , String ip) {
        RefreshToken token = verify(refreshToken);
        token.setRevoked(true);
        refreshTokenRepository.save(token);
        log.info("AUTH_EVENT | action=REFRESH_TOKEN_REFRESH | userId={} | tokenId={} | ip={}",token.getUser().getId(),token.getId(),ip);

    }

}
