package BTEC.ASM.project.modules.academic.seeder;

import BTEC.ASM.project.modules.academic.repository.TermRepository;
import BTEC.ASM.project.modules.academic.service.faker.TermFakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class TermSeeder implements CommandLineRunner {

    private final TermRepository termRepository;
    private final TermFakerService termFakerService;

    @Override
    public void run(String... args) {
        for (int i = 0; i < 12; i++) {
            var term = termFakerService.fake(i);

            if (termRepository.existsByTermCode(term.getTermCode())) {
                continue;
            }
            termRepository.save(term);
        }
    }
}
