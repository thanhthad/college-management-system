package BTEC.ASM.project.modules.academic.service.faker;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import BTEC.ASM.project.modules.academic.entity.ClassGroup;

@Service
public class ClassGroupFakerService {

    private final Faker faker = new Faker();

    public ClassGroup fake(int index) {
        return ClassGroup.builder()
                .groupName("SE-" + (char) ('A' + index))
                .campusCode(faker.options().option(
                        "HN",
                        "HCM",
                        "DN"
                ))
                .departmentCode(faker.options().option(
                        "SE",
                        "IT",
                        "CS"
                ))
                .build();
    }
}
