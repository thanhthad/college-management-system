package BTEC.ASM.project.modules.identity.service;
import BTEC.ASM.project.modules.identity.entity.RefreshToken;
import BTEC.ASM.project.modules.identity.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RefreshTokenService {

    RefreshToken create(User user,String ip);

    RefreshToken verify(String token);

    RefreshToken findValidByUser(User user , String ip);

    void revoke(String refreshToken, String ip);

    String generateAccessToken(String refreshToken, String ip);
}
