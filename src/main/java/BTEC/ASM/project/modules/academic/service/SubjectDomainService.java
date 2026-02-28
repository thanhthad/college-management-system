package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.entity.Subject;

public interface SubjectDomainService {
    Subject getBySubjectCode(String subjectCode);

    boolean existsBySubjectCode(String subjectCode);
}
