package BTEC.ASM.project.modules.academic.seeder;

import BTEC.ASM.project.modules.academic.repository.ClassGroupRepository;
import BTEC.ASM.project.modules.academic.service.faker.ClassGroupFakerService;
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

        long count = classGroupRepository.count();
        while (count < target) {
            var group = classGroupFakerService.fake(index++);

            if (classGroupRepository.existsByGroupName(group.getGroupName())) {
                continue;
            }
            count ++;
            classGroupRepository.save(group);
        }
    }
}
