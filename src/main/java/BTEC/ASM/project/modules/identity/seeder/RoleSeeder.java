package BTEC.ASM.project.modules.identity.seeder;

import BTEC.ASM.project.modules.identity.entity.Role;
import BTEC.ASM.project.modules.identity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(5)
@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        seedRole("STUDENT", "Sinh viên");
        seedRole("LECTURER", "Giảng viên");
        seedRole("TRAINING", "Đào tạo");
        seedRole("EXAMINATION", "Khảo thí");
    }

    private void seedRole(String roleCode, String roleName) {
        roleRepository.findByRoleCode(roleCode)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setRoleCode(roleCode);
                    role.setRoleName(roleName);
                    return roleRepository.save(role);
                });
    }
}
