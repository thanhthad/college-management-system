package BTEC.ASM.project.academic.seeder;

import BTEC.ASM.project.academic.repository.ClassGroupRepository;
import BTEC.ASM.project.academic.service.faker.ClassGroupFakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class ClassGroupSeeder implements CommandLineRunner {

    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupFakerService classGroupFakerService;

    @Override
    public void run(String... args) {
        int target = 60;
        int index = 0;

        while (classGroupRepository.count() < target) {
            var group = classGroupFakerService.fake(index++);

            if (classGroupRepository.existsByGroupName(group.getGroupName())) {
                continue;
            }
            classGroupRepository.save(group);
        }
    }
}
