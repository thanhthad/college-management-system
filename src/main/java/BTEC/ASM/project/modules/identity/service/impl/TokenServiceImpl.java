package BTEC.ASM.project.modules.identity.service.impl;

import BTEC.ASM.project.modules.identity.security.jwt.JwtUtil;
import BTEC.ASM.project.modules.identity.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtUtil jwtUtil;

    @Override
    public TokenPair generateTokens(Long userId, String email, List<String> roles) {

        String accessToken = jwtUtil.generateAccessToken(
                userId,
                email,
                roles
        );

        String refreshToken = jwtUtil.generateRefreshToken(userId);

        return new TokenPair(accessToken, refreshToken);
    }
}
