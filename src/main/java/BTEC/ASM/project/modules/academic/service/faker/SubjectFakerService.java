package BTEC.ASM.project.modules.academic.service.faker;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import BTEC.ASM.project.modules.academic.entity.Subject;

@Service
public class SubjectFakerService {

    private final Faker faker = new Faker();

    public Subject fake() {
        String codePrefix = faker.options().option("CS", "IT", "SE", "AI");

        return Subject.builder()
                .subjectCode(codePrefix + faker.number().numberBetween(100, 499))
                .subjectName(faker.educator().course())
                .description(faker.lorem().sentence(12))
                .build();
    }
}
