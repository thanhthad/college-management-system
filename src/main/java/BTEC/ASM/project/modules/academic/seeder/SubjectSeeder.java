package BTEC.ASM.project.modules.academic.seeder;

import BTEC.ASM.project.modules.academic.repository.SubjectRepository;
import BTEC.ASM.project.modules.academic.service.faker.SubjectFakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class SubjectSeeder implements CommandLineRunner {

    private final SubjectRepository subjectRepository;
    private final SubjectFakerService subjectFakerService;

    @Override
    public void run(String... args) {
        int target = 120;
        long count =subjectRepository.count();
        while (count < target) {
            var subject = subjectFakerService.fake();

            if (subjectRepository.existsBySubjectCode(subject.getSubjectCode())) {
                continue;
            }
            count ++;
            subjectRepository.save(subject);
        }
    }
}
