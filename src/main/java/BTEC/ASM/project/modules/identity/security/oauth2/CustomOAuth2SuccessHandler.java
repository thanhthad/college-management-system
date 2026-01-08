package BTEC.ASM.project.modules.identity.security.oauth2;

import BTEC.ASM.project.modules.identity.service.OAuth2AuthService;
import BTEC.ASM.project.modules.identity.service.TokenService;
import BTEC.ASM.project.modules.identity.service.TokenService.TokenPair;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2AuthService oAuth2AuthService;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        if (email == null || email.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Không lấy được email từ Google"
            );
            return;
        }

        log.info("✅ OAuth2 login success: {}", email);

        // 1️⃣ Xác thực user nội bộ
        OAuth2AuthService.AuthResult authResult =
                oAuth2AuthService.authenticateByEmail(email);

        // 2️⃣ Generate JWT
        TokenPair tokenPair = tokenService.generateTokens(
                authResult.userId(),
                authResult.email(),
                authResult.roles()
        );

        // 3️⃣ Gửi access token qua header (ASM-SAFE)
        response.setHeader(
                "Authorization",
                "Bearer " + tokenPair.accessToken()
        );

        // 4️⃣ Redirect về frontend
        response.sendRedirect("http://localhost:3000/oauth2/success");
    }
}
