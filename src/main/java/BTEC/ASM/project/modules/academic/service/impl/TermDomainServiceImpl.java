package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.entity.Term;
import BTEC.ASM.project.modules.academic.exception.term.TermNotFoundException;
import BTEC.ASM.project.modules.academic.repository.TermRepository;
import BTEC.ASM.project.modules.academic.service.TermDomainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TermDomainServiceImpl implements TermDomainService {
    private final TermRepository termRepository;

    @Override
    public Term getByTermCode(String termCode) {
        Term term = termRepository.findByTermCode(termCode).orElseThrow(
                () -> new TermNotFoundException("Term Not Found")
        );
        return term;
    }

    @Override
    public boolean existsByTermCode(String termCode) {
        return termRepository.existsByTermCode(termCode);
    }
}
