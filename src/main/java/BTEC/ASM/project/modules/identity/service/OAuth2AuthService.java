package BTEC.ASM.project.modules.identity.service;

import java.util.List;

public interface OAuth2AuthService {

    AuthResult authenticateByEmail(String email);

    record AuthResult(
            Long userId,
            String email,
            List<String> roles
    ) {}
}
