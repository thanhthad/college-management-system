package BTEC.ASM.project.modules.identity.seeder;

import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(6)
@AllArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final Faker faker;

    @Override
    public void run(String... args) {
        if (userRepository.count() >= 10) return;

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserCode("USER_" + (i + 1));
            user.setFullName(faker.name().fullName());
            user.setEmail("tauhaitac" + i + "k@gmail.com");

            user.setStatus("ACTIVE");

            userRepository.save(user);
        }
    }
}

