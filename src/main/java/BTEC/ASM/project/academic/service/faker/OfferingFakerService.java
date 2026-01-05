package BTEC.ASM.project.academic.service.faker;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;

import BTEC.ASM.project.academic.entity.Offering;
import BTEC.ASM.project.academic.entity.Subject;
import BTEC.ASM.project.academic.entity.Term;
import BTEC.ASM.project.academic.entity.ClassGroup;

@Service
public class OfferingFakerService {

    private final Faker faker = new Faker();

    public Offering fake(
            List<Subject> subjects,
            List<Term> terms,
            List<ClassGroup> classGroups
    ) {
        Subject subject = faker.options().nextElement(subjects);
        Term term = faker.options().nextElement(terms);
        ClassGroup classGroup = faker.options().nextElement(classGroups);

        return Offering.builder()
                .subject(subject)
                .term(term)
                .classGroup(classGroup)
                .startDate(term.getStartDate())
                .endDate(term.getEndDate())
                .status(faker.options().option(
                        "OPEN",
                        "ONGOING",
                        "CLOSED"
                ))
                .build();
    }
}
