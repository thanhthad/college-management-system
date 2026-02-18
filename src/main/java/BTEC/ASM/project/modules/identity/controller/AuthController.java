package BTEC.ASM.project.modules.identity.controller;

import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.common.utils.IpUtils;
import BTEC.ASM.project.modules.identity.dto.request.RefreshTokenRequest;
import BTEC.ASM.project.modules.identity.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

@Tag(name = "Authentication",description = "Auth APIs")

public class AuthController {

    private final RefreshTokenService refreshTokenService;

    /**
     * 🔁 REFRESH ACCESS TOKEN
     */
    @SecurityRequirement(name = "")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestBody RefreshTokenRequest request,
            HttpServletRequest httpRequest
    ) {
        String newAccessToken =
                refreshTokenService.generateAccessToken(request.getRefreshToken(), IpUtils.getClientIp(httpRequest));

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
            @RequestBody RefreshTokenRequest request,
            HttpServletRequest httpRequest
    ) {

        refreshTokenService.revoke(request.getRefreshToken(),IpUtils.getClientIp(httpRequest));

        return ResponseData.success(null,"Logout successfully",HttpStatus.OK);
    }
}
