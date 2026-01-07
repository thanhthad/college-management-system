package BTEC.ASM.project.modules.identity.service;

import BTEC.ASM.project.modules.identity.dto.request.LoginRequest;
import BTEC.ASM.project.modules.identity.dto.response.LoginResponse;
import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.repository.UserRepository;
import BTEC.ASM.project.modules.identity.repository.UserRoleRepository;
import BTEC.ASM.project.modules.identity.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUserCode(request.getUserCode())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new RuntimeException("Tài khoản chưa được kích hoạt");
        }

        List<String> roles = userRoleRepository
                .findByUser_Id(user.getId())
                .stream()
                .map(ur -> ur.getRole().getRoleCode())
                .toList();

        String accessToken = jwtUtil.generateAccessToken(
                user.getId(),
                user.getEmail(),
                roles
        );

        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        return new LoginResponse(accessToken, refreshToken);
    }
}
