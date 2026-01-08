package BTEC.ASM.project.modules.identity.service.impl;

import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.repository.UserRepository;
import BTEC.ASM.project.modules.identity.repository.UserRoleRepository;
import BTEC.ASM.project.modules.identity.service.OAuth2AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuth2AuthServiceImpl implements OAuth2AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private static final Set<String> ALLOWED_ROLES = Set.of(
            "STUDENT", "LECTURER", "TRAINING", "EXAMINATION"
    );

    @Override
    public AuthResult authenticateByEmail(String email) {

        // 1️⃣ User phải tồn tại trong hệ thống
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new RuntimeException("Tài khoản chưa được nhà trường cấp quyền")
                );

        // 2️⃣ Lấy role hợp lệ
        List<String> roles = userRoleRepository.findByUser_Id(user.getId())
                .stream()
                .map(ur -> ur.getRole().getRoleCode())
                .filter(ALLOWED_ROLES::contains)
                .toList();

        if (roles.isEmpty()) {
            throw new RuntimeException("Tài khoản chưa được phân quyền");
        }

        return new AuthResult(
                user.getId(),
                user.getEmail(),
                roles
        );
    }
}
