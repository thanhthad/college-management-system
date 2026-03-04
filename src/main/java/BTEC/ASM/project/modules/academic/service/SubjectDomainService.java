package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.entity.Subject;

public interface SubjectDomainService {
    Subject getBySubjectCode(String subjectCode);

    Subject getBySubjectId(Long subjectId);

    boolean existsBySubjectId (Long subjectId);

    boolean existsBySubjectCode(String subjectCode);
}
