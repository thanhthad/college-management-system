package BTEC.ASM.project.modules.identity.controller;

import BTEC.ASM.project.modules.identity.entity.RefreshToken;
import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.security.jwt.JwtUtil;
import BTEC.ASM.project.modules.identity.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    /**
     * 🔁 REFRESH ACCESS TOKEN
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestBody Map<String, String> request
    ) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(401).body("Refresh token missing");
        }

        RefreshToken token = refreshTokenService.verify(refreshToken);
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

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken
        ));
    }

    /**
     * 🚪 LOGOUT
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestBody Map<String, String> request
    ) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken != null) {
            RefreshToken token = refreshTokenService.verify(refreshToken);
            refreshTokenService.revoke(token);
        }

        return ResponseEntity.ok("Logged out");
    }
}
