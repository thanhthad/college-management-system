package BTEC.ASM.project.modules.identity.service;
import BTEC.ASM.project.modules.identity.entity.RefreshToken;
import BTEC.ASM.project.modules.identity.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RefreshTokenService {

    RefreshToken create(User user);

    RefreshToken verify(String token);

    Optional<RefreshToken> findOptionalValidByUser(User user);

    void revoke(String refreshToken);

    String generateAccessToken(String refreshToken);
}
