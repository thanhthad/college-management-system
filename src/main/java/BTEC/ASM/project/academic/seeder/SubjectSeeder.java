package BTEC.ASM.project.academic.seeder;

import BTEC.ASM.project.academic.repository.SubjectRepository;
import BTEC.ASM.project.academic.service.faker.SubjectFakerService;
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

        while (subjectRepository.count() < target) {
            var subject = subjectFakerService.fake();

            if (subjectRepository.existsBySubjectCode(subject.getSubjectCode())) {
                continue;
            }
            subjectRepository.save(subject);
        }
    }
}
