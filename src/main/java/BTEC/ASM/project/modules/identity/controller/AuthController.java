package BTEC.ASM.project.modules.identity.controller;

import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.modules.identity.dto.request.RefreshTokenRequest;
import BTEC.ASM.project.modules.identity.entity.RefreshToken;
import BTEC.ASM.project.modules.identity.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RefreshTokenService refreshTokenService;

    /**
     * 🔁 REFRESH ACCESS TOKEN
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        String newAccessToken =
                refreshTokenService.generateAccessToken(request.getRefreshToken());

        return ResponseData.success(
                newAccessToken,
                "Create new access token successfully",
                HttpStatus.OK
        );
    }


    /**
     * 🚪 LOGOUT
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestBody Map<String, String> request
    ) {
        String refreshToken = request.get("refreshToken");

        refreshTokenService.revoke(refreshToken);

        return ResponseData.success(null,"Logout successfully",HttpStatus.OK);
    }
}
