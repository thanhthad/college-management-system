package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.entity.Subject;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectNotFoundException;
import BTEC.ASM.project.modules.academic.repository.SubjectRepository;
import BTEC.ASM.project.modules.academic.service.SubjectDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectDomainImpl implements SubjectDomainService {
    private final SubjectRepository subjectRepository;

    @Override
    public Subject getBySubjectCode(String subjectCode) {
        Subject subject = subjectRepository.findBySubjectCode(subjectCode).orElseThrow(
                () -> new  SubjectNotFoundException("Subject Not Found")
        );
        return subject;
    }

    @Override
    public boolean existsBySubjectCode(String subjectCode) {
        return subjectRepository.existsBySubjectCode(subjectCode);
    }
}
