package BTEC.ASM.project.modules.academic.service.faker;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import BTEC.ASM.project.modules.academic.entity.Term;

@Service
public class TermFakerService {

    private final Faker faker = new Faker();

    public Term fake(int index) {
        LocalDate startDate = LocalDate.of(2025, 1, 1)
                .plusMonths(index * 4);

        return Term.builder()
                .termCode("TERM2025" + (char) ('A' + index))
                .termName(faker.options().option(
                        "Spring 2025",
                        "Summer 2025",
                        "Fall 2025"
                ))
                .startDate(startDate)
                .endDate(startDate.plusMonths(4))
                .build();
    }
}
