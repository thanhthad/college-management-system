package BTEC.ASM.project.modules.identity.service;

import java.util.List;

public interface TokenService {

    TokenPair generateTokens(Long userId, String email, List<String> roles);

    record TokenPair(
            String accessToken,
            String refreshToken
    ) {}
}
