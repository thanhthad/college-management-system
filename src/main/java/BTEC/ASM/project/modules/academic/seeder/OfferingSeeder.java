package BTEC.ASM.project.modules.academic.seeder;

import BTEC.ASM.project.modules.academic.repository.OfferingRepository;
import BTEC.ASM.project.modules.academic.repository.SubjectRepository;
import BTEC.ASM.project.modules.academic.repository.TermRepository;
import BTEC.ASM.project.modules.academic.repository.ClassGroupRepository;
import BTEC.ASM.project.modules.academic.service.faker.OfferingFakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
@RequiredArgsConstructor
public class OfferingSeeder implements CommandLineRunner {

    private final OfferingRepository offeringRepository;
    private final OfferingFakerService offeringFakerService;
    private final SubjectRepository subjectRepository;
    private final TermRepository termRepository;
    private final ClassGroupRepository classGroupRepository;

    @Override
    public void run(String... args) {

        var subjects = subjectRepository.findAll();
        var terms = termRepository.findAll();
        var groups = classGroupRepository.findAll();

        if (subjects.isEmpty() || terms.isEmpty() || groups.isEmpty()) {
            return;
        }

        int target = 2000;
        long count = offeringRepository.count();
        while (count < target) {
            var offering = offeringFakerService.fake(subjects, terms, groups);

            boolean exists =
                    offeringRepository.existsBySubjectAndTermAndClassGroup(
                            offering.getSubject(),
                            offering.getTerm(),
                            offering.getClassGroup()
                    );

            if (exists) continue;
            count ++;

            offeringRepository.save(offering);
        }
    }
}
