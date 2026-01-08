package BTEC.ASM.project.modules.identity.seeder;

import BTEC.ASM.project.modules.identity.entity.Role;
import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.entity.UserRole;
import BTEC.ASM.project.modules.identity.entity.UserRoleId;
import BTEC.ASM.project.modules.identity.repository.RoleRepository;
import BTEC.ASM.project.modules.identity.repository.UserRepository;
import BTEC.ASM.project.modules.identity.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Order(7)
@AllArgsConstructor
public class UserRoleSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public void run(String... args) {
        List<User> users = userRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        if (users.isEmpty() || roles.isEmpty()) return;

        for (User user : users) {
            // Mỗi user gán 1 role ngẫu nhiên
            Role randomRole = roles.get(new Random().nextInt(roles.size()));

            UserRoleId id = new UserRoleId();
            id.setUserId(user.getId());
            id.setRoleId(randomRole.getId());

            // tránh seed trùng
            if (userRoleRepository.existsById(id)) continue;

            UserRole userRole = new UserRole();
            userRole.setId(id);
            userRole.setUser(user);
            userRole.setRole(randomRole);

            userRoleRepository.save(userRole);
        }
    }
}
