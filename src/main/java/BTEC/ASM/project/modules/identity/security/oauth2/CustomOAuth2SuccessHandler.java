package BTEC.ASM.project.modules.identity.security.oauth2;

import BTEC.ASM.project.modules.identity.entity.RefreshToken;
import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.repository.UserRepository;
import BTEC.ASM.project.modules.identity.security.jwt.JwtUtil;
import BTEC.ASM.project.modules.identity.service.RefreshTokenService;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    private static final String FRONTEND_REDIRECT_URL =
            "http://localhost:3000/oauth2/success";

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");

        if (email == null || email.isBlank()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email not found");
            return;
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not allowed"));

        List<String> roles = user.getUserRoles()
                .stream()
                .map(ur -> ur.getRole().getRoleCode())
                .toList();

        // Always generate new access token
        String accessToken = jwtUtil.generateAccessToken(
                user.getId(),
                user.getUserCode(),
                roles
        );

        // Reuse refresh token if still valid
        RefreshToken refreshToken = refreshTokenService
                .findOptionalValidByUser(user)
                .orElseGet(() -> refreshTokenService.create(user));

        String redirectUrl = FRONTEND_REDIRECT_URL
                + "?accessToken=" + URLEncoder.encode(accessToken, StandardCharsets.UTF_8)
                + "&refreshToken=" + URLEncoder.encode(refreshToken.getToken(), StandardCharsets.UTF_8);

        response.sendRedirect(redirectUrl);
    }
}
